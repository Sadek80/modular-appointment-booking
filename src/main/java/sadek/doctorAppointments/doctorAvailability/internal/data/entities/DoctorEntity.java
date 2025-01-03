package sadek.doctorAppointments.doctorAvailability.internal.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sadek.doctorAppointments.doctorAvailability.internal.data.config.DoctorAvailabilityConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "doctors", schema = DoctorAvailabilityConfig.SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
    @Id
    @Column(name = "doctor_id", nullable = false, unique = true, updatable = false)
    private UUID doctorId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SlotEntity> slots = new ArrayList<>();
}
