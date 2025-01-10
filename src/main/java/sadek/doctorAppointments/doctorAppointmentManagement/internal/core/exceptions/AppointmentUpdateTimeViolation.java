package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentUpdateTimeViolation extends DomainException {
    public AppointmentUpdateTimeViolation() {
        super("", Error.NONE);
    }

    public AppointmentUpdateTimeViolation(String message) {
        super(message, Error.NONE);
    }

    public AppointmentUpdateTimeViolation(String message, Error error) {
        super(message, error);
    }

    public AppointmentUpdateTimeViolation(Error error) {
        super("", error);
    }
}

