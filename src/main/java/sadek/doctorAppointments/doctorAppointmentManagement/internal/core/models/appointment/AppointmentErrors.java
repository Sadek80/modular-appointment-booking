package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment;

import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentErrors {
    public static final Error UPDATE_LOCKED = Error.failure("Appointment.UpdateLocked", "Appointment cannot be updated at this time.");
    public static final Error UPDATE_STAGE_VIOLATION = Error.failure("Appointment.UpdateStageViolation", "Appointment cannot be updated at this stage");
    public static Error NOT_FOUND = Error.notFound("Appointment.NotFound", "Cannot Find Appointment");
    public static Error ALREADY_EXISTS = Error.conflict("Appointment.AlreadyExistS", "Appointment already exists");
}
