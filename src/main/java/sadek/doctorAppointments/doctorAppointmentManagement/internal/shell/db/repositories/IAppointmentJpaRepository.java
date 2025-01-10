package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.entities.AppointmentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("DoctorAppointmentManagement-IAppointmentJpaRepository")
public interface IAppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {
    List<AppointmentEntity> findAllByDoctorId(UUID doctorId);
    Optional<AppointmentEntity> findById(UUID appointmentId);
}
