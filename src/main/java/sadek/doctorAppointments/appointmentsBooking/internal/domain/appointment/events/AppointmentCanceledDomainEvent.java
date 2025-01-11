package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.util.UUID;

public record AppointmentCanceledDomainEvent(UUID appointmentId) implements IDomainEvent {
}
