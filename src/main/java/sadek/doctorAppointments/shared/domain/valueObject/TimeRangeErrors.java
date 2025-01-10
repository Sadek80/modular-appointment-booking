package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.Error;

public class TimeRangeErrors {
    public static Error PAST_TIME_RANGE = Error.failure("TimeRange.TimeInThePast", "time must be in the future");
    public static Error INVALID_TIME_RANGE = Error.failure("TimeRange.InvalidTime", "time must be before end time and in same day");
}
