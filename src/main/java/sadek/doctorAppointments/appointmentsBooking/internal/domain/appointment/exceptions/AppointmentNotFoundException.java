package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions;

import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentErrors;
import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentNotFoundException extends DomainException {
    public AppointmentNotFoundException() {
        super("", AppointmentErrors.NOT_FOUND);
    }

    public AppointmentNotFoundException(String message) {
        super(message, AppointmentErrors.NOT_FOUND);
    }

    public AppointmentNotFoundException(String message, Error error) {
        super(message, error);
    }

    public AppointmentNotFoundException(Error error) {
        super("", error);
    }}
