package sadek.doctorAppointments.doctorAvailability.internal.business.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;

public class SlotUpdateViolation extends DomainException {
    public SlotUpdateViolation() {
        super("");
    }

    public SlotUpdateViolation(String message) {
        super(message);
    }
}
