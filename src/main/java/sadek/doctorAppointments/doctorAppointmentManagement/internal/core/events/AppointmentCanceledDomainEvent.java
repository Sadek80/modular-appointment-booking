package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCanceledDomainEvent(
        UUID appointmentId,
        LocalDateTime canceledAt
) implements IDomainEvent {
}
