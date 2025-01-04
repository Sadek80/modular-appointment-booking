package sadek.doctorAppointments.appointmentsBooking.internal.domain.patient;

import sadek.doctorAppointments.shared.domain.Entity;

public class Patient extends Entity<PatientId> {
    private final String name;

    private Patient(PatientId patientId, String name) {
        setId(patientId);
        this.name = name;
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