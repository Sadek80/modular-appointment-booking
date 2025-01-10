package sadek.doctorAppointments.doctorAppointmentManagement.core.outputPorts.repositories;

import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.doctorAppointmentManagement.core.dto.AppointmentResponseDto;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository extends IBaseRepository<Appointment> {
    Appointment findBySlotId(UUID uuid);
    List<AppointmentResponseDto> getAllAppointments(UUID doctorId);
}
