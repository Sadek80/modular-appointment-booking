package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.Error;

public class CostErrors {
    public static sadek.doctorAppointments.shared.domain.Error unSufficientCost = Error.failure("Cost.UnSufficientCost", "Cost must be greater than zero");
}