package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.InvalidSlotCost;
import sadek.doctorAppointments.shared.domain.IValueObject;

public record SlotCost(Double value) implements IValueObject {
    public SlotCost {
        if (value <= 0) {
            throw new InvalidSlotCost(SlotErrors.unSufficientCost);
        }
    }
}
