package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.exceptions.InvalidTimeRange;
import sadek.doctorAppointments.shared.domain.IValueObject;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeRange(LocalDateTime startTime, LocalDateTime endTime) implements IValueObject {
    public TimeRange {
        if (startTime.isAfter(endTime) || isNotSameDay(startTime, endTime)) {
            throw new InvalidTimeRange(TimeRangeErrors.invalidTimeRange);
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
            throw new InvalidTimeRange(TimeRangeErrors.pastTimeRange);
        }
    }
}
