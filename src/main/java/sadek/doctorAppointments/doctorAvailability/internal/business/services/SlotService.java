package sadek.doctorAppointments.doctorAvailability.internal.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.CreateSlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.authentication.DoctorContext;
import sadek.doctorAppointments.doctorAvailability.internal.business.mappers.SlotMapper;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.DoctorId;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.SlotResponseDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.SlotErrors;
import sadek.doctorAppointments.doctorAvailability.internal.data.config.DoctorAvailabilityConfig;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.IDoctorRepository;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.ISlotRepository;
import sadek.doctorAppointments.shared.domain.abstractions.IDateTimeProvider;
import sadek.doctorAppointments.shared.domain.abstractions.IEventBus;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotService {
    private final DoctorContext doctorContext;
    private final ISlotRepository slotRepository;
    private final IDoctorRepository doctorRepository;
    private final SlotMapper slotMapper;
    private final IDateTimeProvider dateTimeProvider;
    private final IEventBus eventBus;

    @Transactional(DoctorAvailabilityConfig.TRANSACTION_MANAGER)
    public Result<Response<UUID>> createSlot(CreateSlotDto request) {
        List<Slot> existingSlots = getDoctorExistingSLots();

        Slot newSlot = Slot.create(DoctorId.from(doctorContext.getUserId()),
                                   request.startTime(),
                                   request.endTime(),
                                   request.cost(),
                                   dateTimeProvider.nowDateTime());
        newSlot.validateNoOverlapWith(existingSlots);

        slotRepository.save(constructSlotEntity(newSlot));
        publishEvents(newSlot);

        return Result.success(
                Response.create(newSlot.getDoctorId().value(), "New slot created")
        );
    }

    public Result<Response<List<SlotResponseDto>>> getAllSlots() {
        List<SlotEntity> slotEntities = slotRepository.findAllByDoctorIdWithDoctor(doctorContext.getUserId());
        List<SlotResponseDto> slotDtoList = slotMapper.mapToSlotResponseDtoList(slotEntities);

        return Result.success(
                Response.create(slotDtoList)
        );
    }

    @Transactional(DoctorAvailabilityConfig.TRANSACTION_MANAGER)
    public Result<Void> updateSlot(UUID slotId, CreateSlotDto request) {
        SlotEntity slotEntity = slotRepository.findSlotBySlotId(slotId).orElse(null);
        Slot slot = slotMapper.mapToSlot(slotEntity);

        if (slot == null) {
            return Result.failure(SlotErrors.NOT_FOUND);
        }

        List<Slot> existingSlots = getDoctorExistingSLots();

        slot.update(request.startTime(),
                    request.endTime(),
                    request.cost(),
                    dateTimeProvider.nowDateTime());
        slot.validateNoOverlapWith(existingSlots);

        slotRepository.save(updateSlotEntity(slot, slotEntity));
        publishEvents(slot);

        return Result.success();
    }

    private List<Slot> getDoctorExistingSLots() {
        List<SlotEntity> existingSlotEntities = slotRepository.findAllByDoctorId(doctorContext.getUserId());
        return slotMapper.mapToSlotList(existingSlotEntities);
    }

    private SlotEntity constructSlotEntity(Slot slot) {
        SlotEntity slotEntity = slotMapper.mapToSlotEntity(slot);
        slotEntity.setDoctor(doctorRepository.getReferenceById(doctorContext.getUserId()));

        return slotEntity;
    }

    private SlotEntity updateSlotEntity(Slot slot, SlotEntity slotEntity) {
        return slotMapper.mapToSlotEntity(slot, slotEntity);
    }

    private void publishEvents(Slot newSlot) {
        if (!newSlot.occurredEvents().isEmpty()) {
            newSlot.occurredEvents().forEach(eventBus::publish);
            newSlot.clearDomainEvents();
        }
    }
}
