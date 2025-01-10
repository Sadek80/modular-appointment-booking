package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
        UUID appointmentId,
        UUID doctorId,
        UUID patientId,
        String patientName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime reservedAt,
        LocalDateTime cancelledAt,
        LocalDateTime completedAt,
        Double cost,
        String status
) {
}