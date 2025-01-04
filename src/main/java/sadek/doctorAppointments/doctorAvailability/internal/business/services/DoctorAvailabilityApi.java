package sadek.doctorAppointments.doctorAvailability.internal.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.doctorAvailability.internal.business.mappers.SlotMapper;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.SlotErrors;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.ISlotRepository;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;
import sadek.doctorAppointments.shared.domain.IEventBus;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityApi implements IDoctorAvailabilityApi {
    private final ISlotRepository slotRepository;
    private final SlotMapper slotMapper;
    private final IEventBus eventBus;

    @Override
    @Transactional
    public Result<Void> releaseSlot(UUID slotId) {
        SlotEntity slotEntity = slotRepository.findSlotBySlotId(slotId).orElse(null);
        Slot slot = slotMapper.mapToSlot(slotEntity);

        if (slot == null) {
            return Result.failure(SlotErrors.notFound);
        }

        slot.release();

        slotRepository.save(updateSlotEntity(slot, slotEntity));
        publishEvents(slot);

        return Result.success();
    }

    @Override
    @Transactional
    public Result<Void> reserveSlot(UUID slotId) {
        throw  new RuntimeException("This method should not be called");
//        Slot slot = getSlot(slotId);
//
//        if (slot == null) {
//            return Result.failure(SlotErrors.notFound);
//        }
//
//        slot.reserve();
//
//        slotRepository.save(constructSlotEntity(slot));
//        publishEvents(slot);
//
//        return Result.success();
    }

    @Override
    public Result<List<SlotDto>> getDoctorAvailableSlots(UUID doctorId) {
        List<SlotEntity> slotEntities = slotRepository.findAllDoctorAvailableSlots(doctorId);
        return Result.success(slotMapper.mapToSlotDtoList(slotEntities));
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
