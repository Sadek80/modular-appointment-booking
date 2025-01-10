package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities;

import jakarta.persistence.*;
import lombok.*;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.config.AppointmentBookingConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "patients", schema = AppointmentBookingConfig.SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientEntity {
    @Id
    @Column(name = "patient_id", nullable = false, unique = true, updatable = false)
    private UUID patientId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments = new ArrayList<>();

    public static PatientEntity from(Patient patient) {
        return PatientEntity.builder()
                .patientId(patient.getId().value())
                .name(patient.getName().value())
                .build();
    }

    public Patient toPatient() {
        return Patient.create(
                this.patientId.toString(),
                this.name
        );
    }
}
