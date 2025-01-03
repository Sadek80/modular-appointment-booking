package sadek.doctorAppointments.doctorAvailability.internal.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.DoctorEntity;

import java.util.UUID;

public interface IDoctorRepository extends JpaRepository<DoctorEntity, UUID>{
}
