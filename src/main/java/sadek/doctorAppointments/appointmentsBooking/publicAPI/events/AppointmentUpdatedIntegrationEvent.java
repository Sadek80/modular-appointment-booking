package sadek.doctorAppointments.appointmentsBooking.publicAPI.events;

import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

import java.time.LocalDateTime;

public record AppointmentUpdatedIntegrationEvent(
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost
) implements IIntegrationEvent {
}
