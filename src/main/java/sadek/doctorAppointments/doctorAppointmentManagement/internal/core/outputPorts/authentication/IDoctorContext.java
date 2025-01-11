package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.authentication;

import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.DoctorId;
import sadek.doctorAppointments.shared.application.IUserContext;

import java.util.UUID;

public interface IDoctorContext extends IUserContext<DoctorId, UUID> {
}
