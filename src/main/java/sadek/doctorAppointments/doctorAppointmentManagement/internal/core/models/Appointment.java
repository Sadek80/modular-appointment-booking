package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models;

import lombok.Getter;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.events.AppointmentCanceledDomainEvent;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.exceptions.AppointmentUpdateStageViolation;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.exceptions.AppointmentUpdateTimeViolation;
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
                Patient.create(patientId, patientName),
                DoctorId.from(doctorId),
                new TimeRange(startTime, endTime),
                new Cost(cost),
                reservedAt,
                AppointmentStatus.BOOKED
        );
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime, double cost) {
        if (isAppointmentCanceled() || isAppointmentCompleted()){
            throw new AppointmentUpdateStageViolation("Appointment Cannot be updated at this stage!",
                    AppointmentErrors.UPDATE_STAGE_VIOLATION);
        }

        this.timeRange = new TimeRange(startTime, endTime);
        this.cost = new Cost(cost);
    }

    public void cancel(LocalDateTime now){
        if (isAppointmentCompleted()){
            throw new AppointmentUpdateStageViolation("Appointment Cannot be canceled at this stage!", AppointmentErrors.UPDATE_STAGE_VIOLATION);
        }

        validateAppointmentUpdateEligibility(now);

        this.status = AppointmentStatus.CANCELLED;
        this.canceledAt = now;

        raiseDomainEvent(new AppointmentCanceledDomainEvent(
                this.getId().value(),
                this.canceledAt
        ));
    }

    public void complete(LocalDateTime now){
        if (isAppointmentCanceled()){
            throw new AppointmentUpdateStageViolation("Appointment Cannot be completed at this stage!",
                    AppointmentErrors.UPDATE_STAGE_VIOLATION);
        }

        if (!isAppointmentFinished(now)){
            throw new AppointmentUpdateTimeViolation("Appointment can only be completed after it is finished",
                    AppointmentErrors.UPDATE_LOCKED);
        }

        this.status = AppointmentStatus.COMPLETED;
        this.completedAt = now;
    }

    private boolean isAppointmentFinished(LocalDateTime now) {
        return now.isAfter(this.timeRange.endTime());
    }

    private boolean isAppointmentCanceled() {
        return status == AppointmentStatus.CANCELLED;
    }

    private boolean isAppointmentCompleted() {
        return status == AppointmentStatus.COMPLETED;
    }

    private void validateAppointmentUpdateEligibility(LocalDateTime now) {
        if (isViolateUpdateHourWindow(now)) {
            throw new AppointmentUpdateTimeViolation(AppointmentErrors.UPDATE_LOCKED);
        }
    }

    private boolean isViolateUpdateHourWindow(LocalDateTime now) {
        return now.isAfter(this.timeRange.startTime().minusHours(MAX_HOURS_BEFORE_APPOINTMENT_ACTIONS));
    }
}
