package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.Appointment;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.repositories.IAppointmentRepository;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.AppointmentMapper;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.entities.AppointmentEntity;
import sadek.doctorAppointments.shared.application.IPublisher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository("DoctorAppointmentManagement-AppointmentRepository")
@RequiredArgsConstructor
public class AppointmentRepository implements IAppointmentRepository {
    private final IAppointmentJpaRepository appointmentJpaRepository;
    private final IPublisher publisher;

    @Override
    public void save(Appointment model) {
        AppointmentEntity appointmentEntity = AppointmentEntity.from(model);
        appointmentJpaRepository.save(appointmentEntity);

        model.occurredEvents().forEach(publisher::publish);
        model.clearDomainEvents();
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments(UUID doctorId, LocalDateTime now) {
        List<AppointmentEntity> appointmentEntities = appointmentJpaRepository.findAllByDoctorId(doctorId, now);
        return AppointmentMapper.toAppointmentResponseDtoList(appointmentEntities);
    }

    @Override
    public Appointment findById(UUID appointmentId) {
        AppointmentEntity appointmentEntity = appointmentJpaRepository.findById(appointmentId).orElse(null);

        if (appointmentEntity == null) {
            return null;
        }

        return appointmentEntity.toAppointment();
    }
}
