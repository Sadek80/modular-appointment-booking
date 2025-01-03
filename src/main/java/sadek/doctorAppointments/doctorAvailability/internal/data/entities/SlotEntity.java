package sadek.doctorAppointments.doctorAvailability.internal.data.entities;

import jakarta.persistence.*;
import lombok.*;
import sadek.doctorAppointments.doctorAvailability.internal.data.config.DoctorAvailabilityConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "slots", schema = DoctorAvailabilityConfig.SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlotEntity {
    @Id
    @Column(name = "slot_id", nullable = false, unique = true, updatable = false)
    private UUID slotId;

    @Column(name = "doctor_id", nullable = false, insertable = false, updatable = false)
    private UUID doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "is_reserved", nullable = false)
    private boolean isReserved;
}
