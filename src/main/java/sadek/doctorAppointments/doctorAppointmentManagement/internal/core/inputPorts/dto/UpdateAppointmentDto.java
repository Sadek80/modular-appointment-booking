package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateAppointmentDto(
        UUID appointmentId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) {
}
