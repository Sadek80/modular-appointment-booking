package sadek.doctorAppointments.appointmentsBooking.internal.domain.patient;

import lombok.Getter;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.valueObject.Name;

@Getter
public class Patient extends Entity<PatientId> {
    private final Name name;

    private Patient(PatientId patientId, String name) {
        setId(patientId);
        this.name = Name.of(name);
    }

    public static Patient create (String name){
        PatientId doctorId = PatientId.generate();
        return new Patient(doctorId, name);
    }

    public static Patient create (String id, String name){
        PatientId patientId = PatientId.fromString(id);
        return new Patient(patientId, name);
    }
}