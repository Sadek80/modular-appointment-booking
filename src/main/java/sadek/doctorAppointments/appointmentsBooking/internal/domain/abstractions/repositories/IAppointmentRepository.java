package sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories;

import sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments.AppointmentResponseDto;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAppointmentRepository extends IBaseRepository<Appointment> {
    Boolean isBookingOverlapping(UUID patientId, LocalDateTime startDate, LocalDateTime endDate, AppointmentStatus status);

    @Override
    void save(Appointment entity);

    List<AppointmentResponseDto> getAllAppointments(UUID patientId);

    Appointment findBySlotId(UUID slotId);
    Appointment findById(UUID appointmentId);
}
