package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.repositories;

import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.Appointment;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository extends IBaseRepository<Appointment> {
    Appointment findById(UUID appointmentId);
    List<AppointmentResponseDto> getAllAppointments(UUID doctorId, LocalDateTime now);
}
