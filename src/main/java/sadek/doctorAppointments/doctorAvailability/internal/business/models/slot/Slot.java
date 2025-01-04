package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import lombok.Getter;
import sadek.doctorAppointments.doctorAvailability.internal.business.events.SlotUpdatedEvent;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.InvalidSlotTimeRange;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.SlotUpdateViolation;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.DoctorId;
import sadek.doctorAppointments.shared.domain.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Slot extends Entity<SlotId> {
    private final DoctorId doctorId;
    private TimeRange timeRange;
    private SlotCost cost;
    private boolean isReserved;

    public static Integer MAX_HOURS_BEFORE_SLOT_UPDATE = 2;
    public static Integer MAX_SLOT_HOURS_DURATION = 2;

    private Slot(SlotId slotId, DoctorId doctorId, TimeRange timeRange, SlotCost cost) {
        setId(slotId);
        this.doctorId = doctorId;
        this.timeRange = timeRange;
        this.cost = cost;
        this.isReserved = false;
    }

    public Slot(UUID id, UUID doctorId, LocalDateTime startTime, LocalDateTime endTime, Double cost) {
        this.setId(new SlotId(id));
        this.doctorId = new DoctorId(doctorId);
        this.timeRange = new TimeRange(startTime, endTime);
        this.cost = new SlotCost(cost);
    }

    public static Slot create(DoctorId doctorId,
                              LocalDateTime startTime,
                              LocalDateTime endTime,
                              Double cost,
                              LocalDateTime now)
    {
        TimeRange timeRange = new TimeRange(startTime, endTime);
        timeRange.validateNewTimeRangeNotInThePast(now);

        validateTimeRangeDuration(timeRange);

        SlotCost slotCost = new SlotCost(cost);

        return new Slot(SlotId.generate(), doctorId, timeRange, slotCost);
    }

    public void update(LocalDateTime startTime,
                       LocalDateTime endTime,
                       Double cost,
                       LocalDateTime now)
    {
        validateSlotUpdateEligibility(now);

        TimeRange newTimeRange = new TimeRange(startTime, endTime);
        validateTimeRangeUpdate(newTimeRange, now);

        this.cost = new SlotCost(cost);
        this.timeRange = newTimeRange;

        raiseDomainEvent(new SlotUpdatedEvent(this.getId().value(),
                                              this.timeRange.startTime(),
                                              this.timeRange.endTime(),
                                              this.cost.value()));
    }

    public void reserve(){
        //TODO Write Logic and validation if any
        this.isReserved = true;
    }

    public void release(){
        //TODO Write Logic and validation if any
        this.isReserved = false;
    }

    private void validateSlotUpdateEligibility(LocalDateTime now) {
        if (this.isReserved && now.isAfter(this.timeRange.startTime().minusHours(MAX_HOURS_BEFORE_SLOT_UPDATE))) {
            throw new SlotUpdateViolation(SlotErrors.updateTimeViolated);
        }
    }

    private void validateTimeRangeUpdate(TimeRange newTimeRange, LocalDateTime now) {
        timeRange.validateNewTimeRangeNotInThePast(now);
        validateTimeRangeDuration(newTimeRange);

        if (this.isReserved && !this.timeRange.isSameDayWith(newTimeRange)){
            throw new SlotUpdateViolation(SlotErrors.updateDateViolated);
        }
    }

    private static void validateTimeRangeDuration(TimeRange timeRange) {
        if (timeRange.durationInHours() > MAX_SLOT_HOURS_DURATION) {
            throw new InvalidSlotTimeRange(SlotErrors.durationExceeded);
        }
    }

    public void validateNoOverlapWith(List<Slot> existingSlots) {
        boolean hasOverlap = existingSlots.stream()
                .filter(slot -> !slot.getId().equals(this.getId()))
                .anyMatch(existingSlot -> existingSlot.getTimeRange().overlapsWith(this.timeRange));

        if (hasOverlap) {
            throw new InvalidSlotTimeRange(SlotErrors.overlapped);
        }
    }
}
