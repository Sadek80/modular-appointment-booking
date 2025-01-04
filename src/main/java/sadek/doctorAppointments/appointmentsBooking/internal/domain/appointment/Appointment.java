package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment;

import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.PatientId;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.doctor.DoctorId;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;

public class Appointment extends Entity<AppointmentId> {
    private final PatientId patientId;
    private final DoctorId doctorId;
    private final SlotId slotId;
    private final TimeRange timeRange;
    private final Cost cost;
    private AppointmentStatus status;
    private LocalDateTime reservedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime completedAt;

    public Appointment(PatientId patientId, DoctorId doctorId, SlotId slotId, TimeRange timeRange, Cost cost) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotId = slotId;
        this.timeRange = timeRange;
        this.cost = cost;
    }
}
