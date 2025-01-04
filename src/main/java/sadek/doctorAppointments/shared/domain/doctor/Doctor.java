package sadek.doctorAppointments.shared.domain.doctor;

import sadek.doctorAppointments.shared.domain.Entity;

public class Doctor extends Entity<DoctorId> {
    private final String name;

    private Doctor(DoctorId doctorId, String name) {
        setId(doctorId);
        this.name = name;
    }

    public static Doctor create(String name) {
        DoctorId doctorId = DoctorId.generate();
        return new Doctor(doctorId, name);
    }

    public static Doctor create(String id, String name) {
        DoctorId doctorId = DoctorId.fromString(id);
        return new Doctor(doctorId, name);
    }
}
