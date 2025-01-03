package sadek.doctorAppointments.doctorAvailability.internal.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.CreateSlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.helpers.DoctorContext;
import sadek.doctorAppointments.doctorAvailability.internal.business.mappers.SlotMapper;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.DoctorId;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.SlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.SlotErrors;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.IDoctorRepository;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.ISlotRepository;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.shared.domain.IDateTimeProvider;
import sadek.doctorAppointments.shared.domain.IEventBus;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotService implements IDoctorAvailabilityApi {
    private final DoctorContext doctorContext;
    private final ISlotRepository slotRepository;
    private final IDoctorRepository doctorRepository;
    private final SlotMapper slotMapper;
    private final IDateTimeProvider dateTimeProvider;
    private final IEventBus eventBus;

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

    public Result<Response<List<SlotDto>>> getAllSlots() {
        List<SlotEntity> slotEntities = slotRepository.findAllByDoctorIdWithDoctor(doctorContext.getUserId());
        List<SlotDto> slotDtoList = slotMapper.mapToSlotDtoList(slotEntities);

        return Result.success(
                Response.create(slotDtoList)
        );
    }

    public Result<Void> updateSlot(UUID slotId, CreateSlotDto request) {
        Slot slot = getSlot(slotId);

        if (slot == null) {
            return Result.failure(SlotErrors.notFound);
        }

        List<Slot> existingSlots = getDoctorExistingSLots();

        slot.update(request.startTime(),
                    request.endTime(),
                    request.cost(),
                    dateTimeProvider.nowDateTime());
        slot.validateNoOverlapWith(existingSlots);

        slotRepository.save(constructSlotEntity(slot));
        publishEvents(slot);

        return Result.success();
    }

    private Slot getSlot(UUID slotId) {
        SlotEntity slotEntity = slotRepository.findById(slotId).orElse(null);
        return slotMapper.mapToSlot(slotEntity);
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

    private void publishEvents(Slot newSlot) {
        if (!newSlot.occurredEvents().isEmpty()) {
            newSlot.occurredEvents().forEach(eventBus::publish);
            newSlot.clearDomainEvents();
        }
    }

    @Override
    public Result<Void> releaseSlot(UUID slotId) {
        Slot slot = getSlot(slotId);

        if (slot == null) {
            return Result.failure(SlotErrors.notFound);
        }

        slot.release();

        slotRepository.save(constructSlotEntity(slot));
        publishEvents(slot);

        return Result.success();
    }
}
