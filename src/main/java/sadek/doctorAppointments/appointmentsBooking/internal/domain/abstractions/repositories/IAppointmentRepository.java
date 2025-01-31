package sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories;

import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IAppointmentRepository extends IBaseRepository<Appointment> {
    Boolean isBookingOverlapping(UUID patientId, LocalDateTime startDate, LocalDateTime endDate, AppointmentStatus status);

    @Override
    void save(Appointment entity);

    Appointment findBySlotId(UUID slotId);
    Appointment findById(UUID appointmentId);
}
