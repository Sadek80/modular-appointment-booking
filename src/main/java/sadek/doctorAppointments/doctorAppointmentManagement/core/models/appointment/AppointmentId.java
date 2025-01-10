package sadek.doctorAppointments.doctorAppointmentManagement.core.models.appointment;

import java.util.UUID;

public record AppointmentId(UUID value) {
    public static AppointmentId generate() {
        return new AppointmentId(UUID.randomUUID());
    }

    public static AppointmentId from(UUID patientId) {
        return new AppointmentId(patientId);
    }

    public static AppointmentId fromString(String patientId) {
        return new AppointmentId(UUID.fromString(patientId));
    }
}
