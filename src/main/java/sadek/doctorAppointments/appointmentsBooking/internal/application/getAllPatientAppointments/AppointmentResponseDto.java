package sadek.doctorAppointments.appointmentsBooking.internal.application.getAllPatientAppointments;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(
        UUID appointmentId,
        UUID doctorId,
        String doctorName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime reservedAt,
        Double cost,
        String status
) {
}
