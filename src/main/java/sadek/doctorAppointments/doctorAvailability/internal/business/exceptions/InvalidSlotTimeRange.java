package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;

public class InvalidSlotTimeRange extends DomainException {
    public InvalidSlotTimeRange() {
        super("");
    }

    public InvalidSlotTimeRange(String message) {
        super(message);
    }
}
