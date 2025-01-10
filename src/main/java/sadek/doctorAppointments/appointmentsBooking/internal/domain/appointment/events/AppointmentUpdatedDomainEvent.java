package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdatedDomainEvent(
        UUID appointmentId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) implements IDomainEvent {
}
