package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.entities;

import jakarta.persistence.*;
import lombok.*;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.Appointment;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.AppointmentId;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.AppointmentStatus;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.doctor.DoctorId;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.patient.Patient;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config.DoctorAppointmentManagementConfig;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments", schema = DoctorAppointmentManagementConfig.SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentEntity {
    @Id
    @Column(name = "appointment_id", nullable = false, unique = true, updatable = false)
    private UUID appointmentId;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "cost", nullable = false)
    private double cost;

    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;

    @Column(name = "completed_at", nullable = true)
    private LocalDateTime completedAt;

    @Column(name = "canceled_at", nullable = true)
    private LocalDateTime canceledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;

    public static AppointmentEntity from(Appointment appointment){
        return AppointmentEntity.builder()
                .appointmentId(appointment.getId().value())
                .doctorId(appointment.getDoctorId().value())
                .patientId(appointment.getPatient().getId().value())
                .patientName(appointment.getPatient().getName().value())
                .startTime(appointment.getTimeRange().startTime())
                .endTime(appointment.getTimeRange().endTime())
                .cost(appointment.getCost().value())
                .reservedAt(appointment.getReservedAt())
                .completedAt(appointment.getCompletedAt())
                .canceledAt(appointment.getCanceledAt())
                .status(appointment.getStatus())
                .build();
    }

    public Appointment toAppointment(){

        return new Appointment(
                AppointmentId.from(this.appointmentId),
                Patient.create(this.getPatientId().toString(), this.patientName),
                DoctorId.from(this.getDoctorId()),
                new TimeRange(this.getStartTime(), this.getEndTime()),
                new Cost(this.getCost()),
                this.getReservedAt(),
                this.getStatus()
        );
    }
}
