package sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotInfoDto(
        UUID slotId,
        UUID doctorId,
        String doctorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost) {
}
