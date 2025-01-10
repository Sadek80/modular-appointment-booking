package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentRuleViolation extends DomainException {
    public AppointmentRuleViolation() {
        super("", Error.NONE);
    }

    public AppointmentRuleViolation(String message) {
        super(message, Error.NONE);
    }

    public AppointmentRuleViolation(String message, Error error) {
        super(message, error);
    }

    public AppointmentRuleViolation(Error error) {
        super("", error);
    }
}
