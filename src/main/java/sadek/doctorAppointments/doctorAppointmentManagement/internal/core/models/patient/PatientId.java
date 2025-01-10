package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.patient;

import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

import java.util.UUID;

public record PatientId(UUID value) implements IValueObject {
    public static PatientId generate() {
        return new PatientId(UUID.randomUUID());
    }

    public static PatientId from(UUID patientId) {
        return new PatientId(patientId);
    }

    public static PatientId fromString(String patientId) {
        return new PatientId(UUID.fromString(patientId));
    }
}