package sadek.doctorAppointments.shared.domain.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidCostException extends DomainException {
    public InvalidCostException() {
        super("", Error.NONE);
    }

    public InvalidCostException(String message) {
        super(message, Error.NONE);
    }

    public InvalidCostException(String message, Error error) {
        super(message, error);
    }

    public InvalidCostException(Error error) {
        super("", error);
    }
}
