package sadek.doctorAppointments.doctorAvailability.internal.business.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.doctorAvailability.internal.business.mappers.SlotMapper;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.SlotErrors;
import sadek.doctorAppointments.doctorAvailability.internal.data.config.DoctorAvailabilityConfig;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;
import sadek.doctorAppointments.doctorAvailability.internal.data.repositories.ISlotRepository;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;
import sadek.doctorAppointments.shared.application.IDateTimeProvider;
import sadek.doctorAppointments.shared.application.IEventBus;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityApi implements IDoctorAvailabilityApi {
    private final ISlotRepository slotRepository;
    private final SlotMapper slotMapper;
    private final IEventBus eventBus;
    private final IDateTimeProvider dateTimeProvider;

    @Override
    @Transactional(value = DoctorAvailabilityConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Void> releaseSlot(UUID slotId) {
        SlotEntity slotEntity = slotRepository.findSlotBySlotId(slotId).orElse(null);
        Slot slot = slotMapper.mapToSlot(slotEntity);

        if (slot == null) {
            return Result.failure(SlotErrors.NOT_FOUND);
        }

        slot.release(dateTimeProvider.nowDateTime());

        slotRepository.save(updateSlotEntity(slot, slotEntity));
        publishEvents(slot);

        return Result.success();
    }

    @Override
    @Transactional(value = DoctorAvailabilityConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Void> reserveSlot(UUID slotId) {
        SlotEntity slotEntity = slotRepository.findActiveSlotBySlotId(slotId).orElse(null);
        Slot slot = slotMapper.mapToSlot(slotEntity);

        if (slot == null) {
            return Result.failure(SlotErrors.NOT_FOUND);
        }

        slot.reserve(dateTimeProvider.nowDateTime());

        slotRepository.save(updateSlotEntity(slot, slotEntity));
        publishEvents(slot);

        return Result.success();
    }

    @Override
    public Result<List<SlotDto>> getDoctorAvailableSlots(UUID doctorId) {
        List<SlotEntity> slotEntities = slotRepository.findAllDoctorAvailableSlots(doctorId);
        return Result.success(slotMapper.mapToSlotDtoList(slotEntities));
    }

    @Override
    @Transactional(value = DoctorAvailabilityConfig.TRANSACTION_MANAGER, readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Result<SlotDto> getSlotById(UUID slotId) {
        SlotDto slot = getActiveSlot(slotId);

        if (slot == null) {
            return Result.failure(SlotErrors.NOT_FOUND);
        }

        return Result.success(slot);
    }

    private SlotDto getActiveSlot(UUID slotId) {
        SlotEntity slotEntity = slotRepository.findActiveSlotBySlotId(slotId).orElse(null);
        return SlotDto.fromSlotEntity(slotEntity);
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
