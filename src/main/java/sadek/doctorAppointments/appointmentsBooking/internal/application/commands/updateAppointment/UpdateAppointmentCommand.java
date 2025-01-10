package sadek.doctorAppointments.appointmentsBooking.internal.application.commands.updateAppointment;

import sadek.doctorAppointments.shared.application.ICommand;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateAppointmentCommand(
        UUID slotId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        double cost
) implements ICommand {
}
