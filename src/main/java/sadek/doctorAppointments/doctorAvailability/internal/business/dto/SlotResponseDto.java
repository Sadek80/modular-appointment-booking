package sadek.doctorAppointments.doctorAvailability.internal.business.dto;

import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotResponseDto(UUID slotId,
                              UUID doctorId,
                              String doctorName,
                              LocalDateTime startTime,
                              LocalDateTime endTime,
                              Double cost,
                              boolean isReserved) {

    public static SlotResponseDto fromSlotEntity(SlotEntity slotEntity) {
        if (slotEntity == null) {
            return null;
        }

        return new SlotResponseDto(
                slotEntity.getSlotId(),
                slotEntity.getDoctorId(),
                slotEntity.getDoctor().getName(),
                slotEntity.getStartTime(),
                slotEntity.getEndTime(),
                slotEntity.getCost(),
                slotEntity.isReserved()
        );
    }

}
