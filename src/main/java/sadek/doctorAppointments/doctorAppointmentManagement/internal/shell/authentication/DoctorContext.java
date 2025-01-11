package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.authentication;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.DoctorId;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.authentication.IDoctorContext;

import java.util.UUID;

@Component("DoctorAppointmentManagement-DoctorContext")
public class DoctorContext implements IDoctorContext {
    private static final UUID DOCTOR_ID = UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454");

    @Override
    public DoctorId getUser() {
        return DoctorId.from(DOCTOR_ID);
    }

    @Override
    public UUID getUserId() {
        return DOCTOR_ID;
    }
}
