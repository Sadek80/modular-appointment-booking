package sadek.doctorAppointments.shared.domain.doctor;

import sadek.doctorAppointments.shared.domain.IValueObject;

import java.util.UUID;

public record DoctorId(UUID value) implements IValueObject {
    public static DoctorId generate() {
        return new DoctorId(UUID.randomUUID());
    }

    public static DoctorId from(UUID doctorId) {
        return new DoctorId(doctorId);
    }

    public static DoctorId fromString(String doctorId) {
        return new DoctorId(UUID.fromString(doctorId));
    }
}