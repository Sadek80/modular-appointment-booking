package sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
        UUID doctorId,
        String doctorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime reservedAt,
        Double cost,
        String status
) {
}
