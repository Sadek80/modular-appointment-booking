package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models;

import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

import java.util.UUID;

public record Patient(UUID patientId, String name) implements IValueObject {
    public static Patient create (UUID patientId, String name){
        return new Patient(patientId, name);
    }
}