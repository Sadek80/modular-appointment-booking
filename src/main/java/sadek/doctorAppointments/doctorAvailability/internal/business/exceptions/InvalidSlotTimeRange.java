package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidSlotTimeRange extends DomainException {
    public InvalidSlotTimeRange() {
        super("", Error.NONE);
    }

    public InvalidSlotTimeRange(String message) {
        super(message, Error.NONE);
    }

    public InvalidSlotTimeRange(String message, Error error) {
        super(message, error);
    }

    public InvalidSlotTimeRange(Error error) {
        super("", error);
    }
}
