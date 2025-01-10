package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities;

import jakarta.persistence.*;
import lombok.*;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentId;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.SlotId;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.doctor.Doctor;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.PatientId;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.config.AppointmentBookingConfig;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments", schema = AppointmentBookingConfig.SCHEMA)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentEntity {
    @Id
    @Column(name = "appointment_id", nullable = false, unique = true, updatable = false)
    private UUID appointmentId;

    @Column(name = "patient_id", nullable = false, insertable = false, updatable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @Column(name = "slot_id", nullable = false, updatable = false)
    private UUID slotId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

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
                .patientId(appointment.getPatientId().value())
                .slotId(appointment.getSlotId().value())
                .doctorId(appointment.getDoctor().getId().value())
                .doctorName(appointment.getDoctor().getName().value())
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
                PatientId.from(this.getPatientId()),
                Doctor.create(this.getDoctorId().toString(), this.getDoctorName()),
                SlotId.from(this.getSlotId()),
                new TimeRange(this.getStartTime(), this.getEndTime()),
                new Cost(this.getCost()),
                this.getReservedAt(),
                this.getCompletedAt(),
                this.getCanceledAt(),
                this.getStatus()
        );
    }
}
