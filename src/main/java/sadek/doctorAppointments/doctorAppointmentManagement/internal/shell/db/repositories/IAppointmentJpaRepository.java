package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.entities.AppointmentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("DoctorAppointmentManagement-IAppointmentJpaRepository")
public interface IAppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {
    @Query("""
        SELECT a
        FROM AppointmentEntity a
        WHERE a.doctorId = :doctorId AND a.startTime >= :startOfDay
""")
    List<AppointmentEntity> findAllByDoctorId(UUID doctorId, LocalDateTime startOfDay);
    Optional<AppointmentEntity> findById(UUID appointmentId);
}
