package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.exceptions.InvalidNameException;

public record Name(String value) {
    public static Name of(String value) {
        if (value == null || value.trim().isEmpty() || value.length() < 2) {
            throw new InvalidNameException("Name is Invalid");
        }

        return new Name(value);
    }
}
