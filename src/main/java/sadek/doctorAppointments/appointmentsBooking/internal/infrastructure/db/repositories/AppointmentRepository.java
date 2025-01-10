package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments.AppointmentResponseDto;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.AppointmentMapper;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.AppointmentEntity;
import sadek.doctorAppointments.shared.domain.IEventBus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AppointmentRepository implements IAppointmentRepository {
    private final IAppointmentJpaRepository appointmentJpaRepository;
    private final IPatientJpaRepository patientJpaRepository;
    private final IEventBus eventBus;

    @Override
    public void save(Appointment model) {
        AppointmentEntity appointmentEntity = AppointmentEntity.from(model);
        appointmentEntity.setPatient(patientJpaRepository.getReferenceById(appointmentEntity.getPatientId()));
        appointmentJpaRepository.save(appointmentEntity);

        model.occurredEvents().forEach(eventBus::publish);
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments(UUID patientId) {
        List<AppointmentEntity> appointmentEntities = appointmentJpaRepository.findAllByPatientId(patientId);
        return AppointmentMapper.toAppointmentResponseDtoList(appointmentEntities);
    }

    @Override
    public Appointment findBySlotId(UUID slotId) {
        AppointmentEntity appointmentEntity = appointmentJpaRepository.findBySlotId(slotId).orElse(null);

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
