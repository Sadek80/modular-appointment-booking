package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment;

import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

import java.util.UUID;

public record Doctor(UUID doctorId, String name) implements IValueObject {

    public static Doctor create(UUID doctorId, String name) {
        return new Doctor(doctorId, name);
    }
}
