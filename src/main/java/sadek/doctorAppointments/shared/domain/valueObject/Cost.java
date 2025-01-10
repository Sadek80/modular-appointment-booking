package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.exceptions.InvalidCostException;
import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

public record Cost(Double value) implements IValueObject {
    public Cost {
        if (value <= 0) {
            throw new InvalidCostException(CostErrors.UN_SUFFICIENT_COST);
        }
    }
}
