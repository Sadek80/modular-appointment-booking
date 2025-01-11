package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import lombok.Getter;
import sadek.doctorAppointments.doctorAvailability.internal.business.events.SlotUpdatedEvent;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.SlotRuleViolation;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.DoctorId;
import sadek.doctorAppointments.shared.domain.Entity;
import sadek.doctorAppointments.shared.domain.valueObject.Cost;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Slot extends Entity<SlotId> {
    private final DoctorId doctorId;
    private TimeRange timeRange;
    private Cost cost;
    private boolean isReserved;

    public static Integer MAX_HOURS_BEFORE_SLOT_UPDATE = 2;
    public static Integer MAX_SLOT_HOURS_DURATION = 2;

    private Slot(SlotId slotId, DoctorId doctorId, TimeRange timeRange, Cost cost) {
        setId(slotId);
        this.doctorId = doctorId;
        this.timeRange = timeRange;
        this.cost = cost;
        this.isReserved = false;
    }

    public Slot(UUID id, UUID doctorId, LocalDateTime startTime, LocalDateTime endTime, Double cost, Boolean isReserved) {
        this.setId(new SlotId(id));
        this.doctorId = new DoctorId(doctorId);
        this.timeRange = new TimeRange(startTime, endTime);
        this.cost = new Cost(cost);
        this.isReserved = isReserved;
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

        Cost slotCost = new Cost(cost);

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

        this.cost = new Cost(cost);
        this.timeRange = newTimeRange;

        if (isReserved) {
            raiseDomainEvent(new SlotUpdatedEvent(this.getId().value(),
                    this.timeRange.startTime(),
                    this.timeRange.endTime(),
                    this.cost.value()));
        }
    }

    public void reserve(LocalDateTime now){
        if (this.isReserved) {
            throw new SlotRuleViolation(SlotErrors.ALREADY_RESERVED);
        }

        if (now.isAfter(this.timeRange.startTime())){
            throw new SlotRuleViolation(SlotErrors.TIME_DUE);
        }

        this.isReserved = true;
    }

    public void release(LocalDateTime now){
        validateSlotUpdateEligibility(now);
        this.isReserved = false;
    }

    private void validateSlotUpdateEligibility(LocalDateTime now) {
        if (this.isReserved && isViolateUpdateHourWindow(now)) {
            throw new SlotRuleViolation(SlotErrors.UPDATE_TIME_VIOLATED);
        }
    }

    private boolean isViolateUpdateHourWindow(LocalDateTime now) {
        return now.isAfter(this.timeRange.startTime().minusHours(MAX_HOURS_BEFORE_SLOT_UPDATE));
    }

    private void validateTimeRangeUpdate(TimeRange newTimeRange, LocalDateTime now) {
        newTimeRange.validateNewTimeRangeNotInThePast(now);
        validateTimeRangeDuration(newTimeRange);

        if (this.isReserved && !this.timeRange.isSameDayWith(newTimeRange)){
            throw new SlotRuleViolation(SlotErrors.UPDATE_DATE_VIOLATED);
        }
    }

    private static void validateTimeRangeDuration(TimeRange timeRange) {
        if (timeRange.durationInHours() > MAX_SLOT_HOURS_DURATION) {
            throw new SlotRuleViolation(SlotErrors.DURATION_EXCEEDED);
        }
    }

    public void validateNoOverlapWith(List<Slot> existingSlots) {
        boolean hasOverlap = existingSlots.stream()
                .filter(slot -> !slot.getId().equals(this.getId()))
                .anyMatch(existingSlot -> existingSlot.getTimeRange().overlapsWith(this.timeRange));

        if (hasOverlap) {
            throw new SlotRuleViolation(SlotErrors.OVERLAPPED);
        }
    }
}
