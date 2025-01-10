package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;

public record AppointmentUpdatedDomainEvent(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) implements IDomainEvent {
}
