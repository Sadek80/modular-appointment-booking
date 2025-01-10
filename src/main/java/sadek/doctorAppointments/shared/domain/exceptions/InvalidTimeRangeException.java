package sadek.doctorAppointments.shared.domain.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidTimeRangeException extends DomainException {
    public InvalidTimeRangeException() {
        super("", Error.NONE);
    }

    public InvalidTimeRangeException(String message) {
        super(message, Error.NONE);
    }

    public InvalidTimeRangeException(String message, Error error) {
        super(message, error);
    }

    public InvalidTimeRangeException(Error error) {
        super("", error);
    }
}
