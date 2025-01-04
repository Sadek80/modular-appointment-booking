package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class SlotRuleViolation extends DomainException {
    public SlotRuleViolation() {
        super("", Error.NONE);
    }

    public SlotRuleViolation(String message) {
        super(message, Error.NONE);
    }

    public SlotRuleViolation(String message, Error error) {
        super(message, error);
    }

    public SlotRuleViolation(Error error) {
        super("", error);
    }
}
