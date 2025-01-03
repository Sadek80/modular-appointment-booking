package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class SlotUpdateViolation extends DomainException {
    public SlotUpdateViolation() {
        super("", Error.NONE);
    }

    public SlotUpdateViolation(String message) {
        super(message, Error.NONE);
    }

    public SlotUpdateViolation(String message, Error error) {
        super(message, error);
    }

    public SlotUpdateViolation(Error error) {
        super("", error);
    }
}
