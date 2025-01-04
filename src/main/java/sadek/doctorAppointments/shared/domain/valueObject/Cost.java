package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.exceptions.InvalidCost;
import sadek.doctorAppointments.shared.domain.IValueObject;

public record Cost(Double value) implements IValueObject {
    public Cost {
        if (value <= 0) {
            throw new InvalidCost(CostErrors.unSufficientCost);
        }
    }
}
