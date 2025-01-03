package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.InvalidSlotTimeRange;
import sadek.doctorAppointments.shared.domain.IValueObject;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeRange(LocalDateTime startTime, LocalDateTime endTime) implements IValueObject {
    public TimeRange {
        if (startTime.isAfter(endTime) || isNotSameDay(startTime, endTime)) {
            throw new InvalidSlotTimeRange("Start time must be before end time and in same day");
        }
    }

    public long durationInHours() {
        return Duration.between(startTime, endTime).toHours();
    }

    public boolean overlapsWith(TimeRange other) {
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }

    public boolean isSameDayWith(TimeRange other) {
        return !(isNotSameDay(this.startTime, other.startTime) || isNotSameDay(this.endTime, other.endTime));
    }

    private boolean isNotSameDay(LocalDateTime startTime, LocalDateTime endTime) {
        return !startTime.toLocalDate().isEqual(endTime.toLocalDate());
    }

    public void validateNewTimeRangeNotInThePast(LocalDateTime now) {
        if (startTime.isBefore(now) || endTime.isBefore(now)) {
            throw new InvalidSlotTimeRange("Slot time must be in the future");
        }
    }
}
