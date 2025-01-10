package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateAppointmentDto(
        UUID appointmentId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost,
        UUID patientId,
        String patientName,
        UUID doctorId,
        LocalDateTime reservedAt
) {
}
