package sadek.doctorAppointments.doctorAppointmentManagement.publicAPI.events;

import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCanceledIntegrationEvent(
        UUID appointmentId,
        LocalDateTime canceledAt
) implements IIntegrationEvent {
}
