package sadek.doctorAppointments.doctorAvailability.publicAPI;

import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotDto(
        UUID slotId,
        UUID doctorId,
        String doctorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) {

    public static SlotDto fromSlotEntity(SlotEntity slotEntity) {
        if (slotEntity == null) {
            return null;
        }

        return new SlotDto(
                slotEntity.getSlotId(),
                slotEntity.getDoctorId(),
                slotEntity.getDoctor().getName(),
                slotEntity.getStartTime(),
                slotEntity.getEndTime(),
                slotEntity.getCost()
        );
    }
}
