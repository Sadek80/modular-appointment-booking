package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.Error;

public class NameErrors {
    public static Error INVALID_NAME = Error.failure("Name.Invalid", "Please enter a valid name.");

}
