package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.AppointmentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {
    List<AppointmentEntity> findAllByPatientId(UUID patientId);

    Optional<AppointmentEntity> findBySlotId(UUID slotId);

    @Query("""
            SELECT TRUE
            FROM AppointmentEntity a
            WHERE a.patientId = :patientId AND
                  a.startTime <= :endTime AND
                  a.endTime >= :startTime AND
                  a.status = :status
""")
    Optional<Boolean> isBookingOverlapping(UUID patientId, LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status);
}
