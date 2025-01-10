package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.repositories;

import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.Appointment;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository extends IBaseRepository<Appointment> {
    Appointment findById(UUID appointmentId);
    List<AppointmentResponseDto> getAllAppointments(UUID doctorId);
}
