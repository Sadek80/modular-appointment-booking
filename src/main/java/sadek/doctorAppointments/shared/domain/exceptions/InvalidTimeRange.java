package sadek.doctorAppointments.shared.domain.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidTimeRange extends DomainException {
    public InvalidTimeRange() {
        super("", Error.NONE);
    }

    public InvalidTimeRange(String message) {
        super(message, Error.NONE);
    }

    public InvalidTimeRange(String message, Error error) {
        super(message, error);
    }

    public InvalidTimeRange(Error error) {
        super("", error);
    }
}
