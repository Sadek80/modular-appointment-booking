package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment;

import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentErrors {
    public static final Error TIME_DUE = Error.failure("Appointment.TimeDue", "Appointment time has passed");
    public static final Error BOOK_TIMING_VIOLATION = Error.failure("Appointment.BookLocked", "Appointment cannot be booked less than 2 hours before its start time");
    public static Error OVERLAPPED = Error.conflict("Appointment.Overlapped", "You had already OVERLAPPED appointments");
    public static Error NOT_FOUND = Error.notFound("Appointment.NotFound", "Cannot Find Appointment");
}
