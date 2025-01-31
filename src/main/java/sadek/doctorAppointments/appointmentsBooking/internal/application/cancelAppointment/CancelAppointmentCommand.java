package sadek.doctorAppointments.appointmentsBooking.internal.application.cancelAppointment;

import sadek.doctorAppointments.shared.application.ICommand;

import java.time.LocalDateTime;
import java.util.UUID;

public record CancelAppointmentCommand(
        UUID appointmentId,
        LocalDateTime canceledAt
) implements ICommand {
}
