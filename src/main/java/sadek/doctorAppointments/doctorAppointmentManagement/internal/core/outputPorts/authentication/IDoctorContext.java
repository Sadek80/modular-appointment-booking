package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.authentication;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.doctor.Doctor;
import sadek.doctorAppointments.shared.application.IUserContext;

import java.util.UUID;

@Component
public interface IDoctorContext extends IUserContext<Doctor, UUID> {
}
