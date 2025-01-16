package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.repositories.IAppointmentListingRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments.AppointmentResponseDto;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.AppointmentMapper;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.AppointmentEntity;
import sadek.doctorAppointments.shared.application.IPublisher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AppointmentRepository implements IAppointmentRepository, IAppointmentListingRepository {
    private final IAppointmentJpaRepository appointmentJpaRepository;
    private final IPatientJpaRepository patientJpaRepository;
    private final IPublisher publish;

    @Override
    public void save(Appointment model) {
        AppointmentEntity appointmentEntity = AppointmentEntity.from(model);
        appointmentEntity.setPatient(patientJpaRepository.getReferenceById(appointmentEntity.getPatientId()));
        appointmentJpaRepository.save(appointmentEntity);

        model.occurredEvents().forEach(publish::publish);
        model.clearDomainEvents();
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments(UUID patientId) {
        List<AppointmentEntity> appointmentEntities = appointmentJpaRepository.findAllByPatientId(patientId);
        return AppointmentMapper.toAppointmentResponseDtoList(appointmentEntities);
    }

    @Override
    public Appointment findBySlotId(UUID slotId) {
        AppointmentEntity appointmentEntity = appointmentJpaRepository.findBySlotId(slotId, AppointmentStatus.BOOKED).orElse(null);

        if (appointmentEntity == null) {
            return null;
        }

        return appointmentEntity.toAppointment();
    }

    @Override
    public Appointment findById(UUID appointmentId) {
        AppointmentEntity appointmentEntity = appointmentJpaRepository.findById(appointmentId).orElse(null);

        if (appointmentEntity == null) {
            return null;
        }

        return appointmentEntity.toAppointment();
    }

    @Override
    public Boolean isBookingOverlapping(UUID patientId, LocalDateTime startDate, LocalDateTime endDate, AppointmentStatus status) {
        return appointmentJpaRepository.isBookingOverlapping(patientId, startDate, endDate, status).orElse(false);
    }
}
