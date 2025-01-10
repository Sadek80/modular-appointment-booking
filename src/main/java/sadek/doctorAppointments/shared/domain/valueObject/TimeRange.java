package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.exceptions.InvalidTimeRangeException;
import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeRange(LocalDateTime startTime, LocalDateTime endTime) implements IValueObject {
    public TimeRange {
        if (startTime.isAfter(endTime) || isNotSameDay(startTime, endTime)) {
            throw new InvalidTimeRangeException(TimeRangeErrors.INVALID_TIME_RANGE);
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
            throw new InvalidTimeRangeException(TimeRangeErrors.PAST_TIME_RANGE);
        }
    }
}
