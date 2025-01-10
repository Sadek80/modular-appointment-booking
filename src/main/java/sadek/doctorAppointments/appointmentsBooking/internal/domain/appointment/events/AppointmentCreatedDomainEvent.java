package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreatedDomainEvent(
        UUID appointmentId,
        UUID slotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost,
        UUID patientId,
        String patientName,
        UUID doctorId,
        LocalDateTime reservedAt
) implements IDomainEvent {
}
