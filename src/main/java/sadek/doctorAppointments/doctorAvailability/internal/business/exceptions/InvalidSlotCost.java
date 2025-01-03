package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidSlotCost extends DomainException {
    public InvalidSlotCost() {
        super("", Error.NONE);
    }

    public InvalidSlotCost(String message) {
        super(message, Error.NONE);
    }

    public InvalidSlotCost(String message, Error error) {
        super(message, error);
    }

    public InvalidSlotCost(Error error) {
        super("", error);
    }
}
