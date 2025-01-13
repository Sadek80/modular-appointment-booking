package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment;

import lombok.Getter;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCanceledDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCreatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentUpdatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.AppointmentRuleViolation;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.InvalidAppointmentTime;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.PatientId;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services.dto.SlotInfoDto;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;

@Getter
public class Appointment extends Entity<AppointmentId> {
    private static final int MAX_HOURS_BEFORE_APPOINTMENT_ACTIONS = 2;

    private final PatientId patientId;
    private final Doctor doctor;
    private final SlotId slotId;
    private TimeRange timeRange;
    private Cost cost;
    private AppointmentStatus status;
    private LocalDateTime reservedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;

    public Appointment(PatientId patientId, Doctor doctor, SlotId slotId, TimeRange timeRange, Cost cost) {
        this.setId(AppointmentId.generate());
        this.patientId = patientId;
        this.doctor = doctor;
        this.slotId = slotId;
        this.timeRange = timeRange;
        this.cost = cost;
    }

    public Appointment(AppointmentId appointmentId, PatientId patientId, Doctor doctor, SlotId slotId, TimeRange timeRange, Cost cost, LocalDateTime reservedAt, LocalDateTime canceledAt, LocalDateTime completedAt, AppointmentStatus status) {
        this.setId(appointmentId);
        this.patientId = patientId;
        this.doctor = doctor;
        this.slotId = slotId;
        this.timeRange = timeRange;
        this.cost = cost;
        this.reservedAt = reservedAt;
        this.canceledAt = canceledAt;
        this.completedAt = completedAt;
        this.status = status;
    }

    public static Appointment book(SlotInfoDto slotInfo, Patient patient, LocalDateTime now) {
        validateBookingTime(slotInfo, now);
        validateBookingTimingCompliance(slotInfo, now);

        Appointment appointment = new Appointment(patient.getId(),
                Doctor.create(slotInfo.doctorId(), slotInfo.doctorName()),
                SlotId.from(slotInfo.slotId()),
                new TimeRange(slotInfo.startTime(), slotInfo.endTime()),
                new Cost(slotInfo.cost()));

        appointment.reservedAt = now;
        appointment.status = AppointmentStatus.BOOKED;

        appointment.raiseDomainEvent(new AppointmentCreatedDomainEvent(
                appointment.getId().value(),
                appointment.slotId.value(),
                appointment.timeRange.startTime(),
                appointment.timeRange.endTime(),
                appointment.cost.value(),
                appointment.patientId.value(),
                patient.getName().value(),
                appointment.doctor.doctorId(),
                appointment.reservedAt));

        return appointment;
    }

    private static void validateBookingTime(SlotInfoDto slotInfo, LocalDateTime now) {
        if (now.isAfter(slotInfo.startTime())){
            throw new InvalidAppointmentTime(AppointmentErrors.TIME_DUE);
        }
    }

    private static void validateBookingTimingCompliance(SlotInfoDto slotInfo, LocalDateTime now) {
        if (now.isAfter(slotInfo.startTime().minusHours(MAX_HOURS_BEFORE_APPOINTMENT_ACTIONS))){
            throw new InvalidAppointmentTime(AppointmentErrors.BOOK_TIMING_VIOLATION);
        }
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime, double cost) {
        this.timeRange = new TimeRange(startTime, endTime);
        this.cost = new Cost(cost);

        raiseDomainEvent(new AppointmentUpdatedDomainEvent(
                this.getId().value(),
                startTime,
                endTime,
                cost
        ));
    }

    public void cancel(LocalDateTime canceledAt, LocalDateTime now) {
        validateAppointmentUpdateEligibility(now);

        this.status = AppointmentStatus.CANCELLED;
        this.canceledAt = now;

        raiseDomainEvent(new AppointmentCanceledDomainEvent(
                this.getId().value(),
                this.patientId.value()
        ));
    }

    private void validateAppointmentUpdateEligibility(LocalDateTime now) {
        if (isViolateUpdateHourWindow(now)) {
            throw new AppointmentRuleViolation(AppointmentErrors.UPDATE_LOCKED);
        }
    }

    private boolean isViolateUpdateHourWindow(LocalDateTime now) {
        return now.isAfter(this.timeRange.startTime().minusHours(MAX_HOURS_BEFORE_APPOINTMENT_ACTIONS));
    }
}
