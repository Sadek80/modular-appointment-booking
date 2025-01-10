package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.doctor;

import lombok.Getter;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.valueObject.Name;

@Getter
public class Doctor extends Entity<DoctorId> {
    private final Name name;

    private Doctor(DoctorId doctorId, String name) {
        setId(doctorId);
        this.name = Name.of(name);
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
