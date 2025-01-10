package sadek.doctorAppointments.appointmentsBooking.publicAPI.events;

import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentCreatedIntegrationEvent(
        UUID appointmentId,
        UUID slotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double cost,
        UUID patientId,
        String patientName,
        UUID doctorId,
        LocalDateTime reservedAt
) implements IIntegrationEvent {
}
