package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.Error;

public class CostErrors {
    public static Error UN_SUFFICIENT_COST = Error.failure("Cost.UnSufficientCost", "Cost must be greater than zero");
}