package sadek.doctorAppointments.shared.domain.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidCost extends DomainException {
    public InvalidCost() {
        super("", Error.NONE);
    }

    public InvalidCost(String message) {
        super(message, Error.NONE);
    }

    public InvalidCost(String message, Error error) {
        super(message, error);
    }

    public InvalidCost(Error error) {
        super("", error);
    }
}
