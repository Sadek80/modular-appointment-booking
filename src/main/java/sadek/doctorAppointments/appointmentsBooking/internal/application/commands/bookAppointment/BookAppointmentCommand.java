package sadek.doctorAppointments.appointmentsBooking.internal.application.commands.bookAppointment;

import sadek.doctorAppointments.shared.application.ICommand;

import java.util.UUID;

public record BookAppointmentCommand(UUID slotId) implements ICommand {
}
