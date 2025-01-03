package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;

public class InvalidSlotCost extends DomainException {
    public InvalidSlotCost() {
        super("");
    }

    public InvalidSlotCost(String message) {
        super(message);
    }
}
