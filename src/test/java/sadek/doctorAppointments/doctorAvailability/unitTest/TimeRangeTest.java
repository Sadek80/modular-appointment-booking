package sadek.doctorAppointments.doctorAvailability.unitTest;

import org.junit.jupiter.api.Test;
import sadek.doctorAppointments.doctorAvailability.internal.business.exceptions.InvalidSlotTimeRange;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.slot.TimeRange;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeRangeTest {

    @Test
    void constructor_validTimeRange_shouldCreateTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 3, 12, 0);

        TimeRange timeRange = new TimeRange(startTime, endTime);

        assertNotNull(timeRange);
        assertEquals(startTime, timeRange.startTime());
        assertEquals(endTime, timeRange.endTime());
    }

    @Test
    void constructor_endTimeBeforeStartTime_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 3, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 3, 10, 0);

        assertThrows(InvalidSlotTimeRange.class, () -> new TimeRange(startTime, endTime));
    }

    @Test
    void constructor_startTimeAndEndTimeNotOnSameDay_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 3, 23, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 4, 1, 0);

        assertThrows(InvalidSlotTimeRange.class, () -> new TimeRange(startTime, endTime));
    }

    @Test
    void durationInHours_validTimeRange_shouldReturnCorrectDuration() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 3, 12, 0);

        TimeRange timeRange = new TimeRange(startTime, endTime);

        assertEquals(2, timeRange.durationInHours());
    }

    @Test
    void overlapsWith_overlappingTimeRange_shouldReturnTrue() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2025, 1, 3, 12, 0);
        TimeRange timeRange1 = new TimeRange(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2025, 1, 3, 11, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2025, 1, 3, 13, 0);
        TimeRange timeRange2 = new TimeRange(startTime2, endTime2);

        assertTrue(timeRange1.overlapsWith(timeRange2));
    }

    @Test
    void overlapsWith_nonOverlappingTimeRange_shouldReturnFalse() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2025, 1, 3, 12, 0);
        TimeRange timeRange1 = new TimeRange(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2025, 1, 3, 12, 1);
        LocalDateTime endTime2 = LocalDateTime.of(2025, 1, 3, 14, 0);
        TimeRange timeRange2 = new TimeRange(startTime2, endTime2);

        assertFalse(timeRange1.overlapsWith(timeRange2));
    }

    @Test
    void isSameDayWith_sameDayTimeRange_shouldReturnTrue() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2025, 1, 3, 12, 0);
        TimeRange timeRange1 = new TimeRange(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2025, 1, 3, 13, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2025, 1, 3, 15, 0);
        TimeRange timeRange2 = new TimeRange(startTime2, endTime2);

        assertTrue(timeRange1.isSameDayWith(timeRange2));
    }

    @Test
    void isSameDayWith_differentDayTimeRange_shouldReturnFalse() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 1, 3, 10, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2025, 1, 3, 12, 0);
        TimeRange timeRange1 = new TimeRange(startTime1, endTime1);

        LocalDateTime startTime2 = LocalDateTime.of(2025, 1, 4, 10, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2025, 1, 4, 12, 0);
        TimeRange timeRange2 = new TimeRange(startTime2, endTime2);

        assertFalse(timeRange1.isSameDayWith(timeRange2));
    }

    @Test
    void validateNewTimeRangeNotInThePast_futureTimeRange_shouldNotThrowException() {
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 4, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 1, 4, 12, 0);
        TimeRange timeRange = new TimeRange(startTime, endTime);

        assertDoesNotThrow(() -> timeRange.validateNewTimeRangeNotInThePast(LocalDateTime.now()));
    }

    @Test
    void validateNewTimeRangeNotInThePast_pastTimeRange_shouldThrowInvalidSlotTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 30, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 30, 12, 0);
        TimeRange timeRange = new TimeRange(startTime, endTime);

        assertThrows(InvalidSlotTimeRange.class, () -> timeRange.validateNewTimeRangeNotInThePast(LocalDateTime.now()));
    }
}
