package sadek.doctorAppointments.doctorAvailability.internal.business.mappers;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.SlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.Slot;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlotMapper {

    public SlotEntity mapToSlotEntity(Slot slot) {
        if (slot == null) {
            return null;
        }

        return SlotEntity.builder()
                .slotId(slot.getId().value())
                .cost(slot.getCost().value())
                .doctorId(slot.getDoctorId().value())
                .startTime(slot.getTimeRange().startTime())
                .endTime(slot.getTimeRange().endTime())
                .isReserved(slot.isReserved())
                .build();
    }

    public Slot mapToSlot(SlotEntity slotEntity) {
        if (slotEntity == null) {
            return null;
        }

        return new Slot(slotEntity.getSlotId(),
                        slotEntity.getDoctorId(),
                        slotEntity.getStartTime(),
                        slotEntity.getEndTime(),
                        slotEntity.getCost());
    }

    public List<SlotEntity> mapToSlotEntityList(List<Slot> slots) {
        return slots.stream().map(this::mapToSlotEntity).collect(Collectors.toList());
    }

    public List<Slot> mapToSlotList(List<SlotEntity> slotEntities) {
        return slotEntities.stream().map(this::mapToSlot).collect(Collectors.toList());
    }

    public List<SlotDto> mapToSlotDtoList(List<SlotEntity> slotEntities) {
        return slotEntities.stream()
                .map(SlotDto::fromSlotEntity)
                .toList();
    }
}