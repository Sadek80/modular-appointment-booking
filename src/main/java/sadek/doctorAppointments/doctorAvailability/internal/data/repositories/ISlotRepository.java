package sadek.doctorAppointments.doctorAvailability.internal.data.repositories;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.doctorAvailability.internal.data.entities.SlotEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ISlotRepository extends JpaRepository<SlotEntity, UUID>{
    List<SlotEntity> findAllByDoctorId(UUID doctorId);

    @Query("""
        SELECT s FROM SlotEntity s JOIN FETCH s.doctor
    """)
    List<SlotEntity> findAllByDoctorIdWithDoctor(UUID doctorId);

    @Query("""
        SELECT s
        FROM SlotEntity s
        JOIN FETCH s.doctor
        WHERE s.doctorId = :doctorId AND s.isReserved = false
    """)
    List<SlotEntity> findAllDoctorAvailableSlots(UUID doctorId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SlotEntity> findSlotBySlotId(UUID slotId);

    @Query("""
        SELECT s
        FROM SlotEntity s
        JOIN FETCH s.doctor
        WHERE s.isReserved = false AND s.slotId = :slotId
    """)
    Optional<SlotEntity> findActiveSlotBySlotId(UUID slotId);
}
