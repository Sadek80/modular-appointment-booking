package sadek.doctorAppointments.doctorAppointmentManagement.core.models.appointment;

import lombok.Getter;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.doctor.DoctorId;
import sadek.doctorAppointments.doctorAppointmentManagement.core.models.patient.Patient;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Appointment extends Entity<AppointmentId> {
    private static final int MAX_HOURS_BEFORE_APPOINTMENT_ACTIONS = 2;

    private final Patient patient;
    private final DoctorId doctorId;
    private TimeRange timeRange;
    private Cost cost;
    private AppointmentStatus status;
    private LocalDateTime reservedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;

    public Appointment(AppointmentId appointmentId, Patient patient, DoctorId doctorId, TimeRange timeRange, Cost cost, LocalDateTime reservedAt, AppointmentStatus status) {
        this.setId(appointmentId);
        this.patient = patient;
        this.doctorId = doctorId;
        this.timeRange = timeRange;
        this.cost = cost;
        this.reservedAt = reservedAt;
        this.status = status;
    }

    public static Appointment create(UUID appointmentId, UUID patientId, String patientName, UUID doctorId, LocalDateTime startTime, LocalDateTime endTime, Double cost, LocalDateTime reservedAt){
        return new Appointment(
                AppointmentId.from(appointmentId),
                Patient.create(patientId.toString(), patientName),
                DoctorId.from(doctorId),
                new TimeRange(startTime, endTime),
                new Cost(cost),
                reservedAt,
                AppointmentStatus.BOOKED
        );
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime, double cost) {
        this.timeRange = new TimeRange(startTime, endTime);
        this.cost = new Cost(cost);
    }
}
