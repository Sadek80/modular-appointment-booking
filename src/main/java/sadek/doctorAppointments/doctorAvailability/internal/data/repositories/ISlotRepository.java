package sadek.doctorAppointments.doctorAvailability.internal.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ISlotRepository extends JpaRepository<SlotEntity, UUID>{
    List<SlotEntity> findAllByDoctorId(UUID doctorId);

    @Query("""
        SELECT s FROM SlotEntity s JOIN FETCH s.doctor
    """)
    List<SlotEntity> findAllByDoctorIdWithDoctor(UUID doctorId);
}
