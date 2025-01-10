package sadek.doctorAppointments.appointmentsBooking.publicAPI.events;

import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentUpdatedIntegrationEvent(
        UUID appointmentId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) implements IIntegrationEvent {
}
