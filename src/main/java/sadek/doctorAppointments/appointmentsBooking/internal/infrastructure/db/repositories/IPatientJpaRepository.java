package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.PatientEntity;

import java.util.UUID;

@Repository
public interface IPatientJpaRepository extends JpaRepository<PatientEntity, UUID> {
}
