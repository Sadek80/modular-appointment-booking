package sadek.doctorAppointments.shared.domain.valueObject;

import sadek.doctorAppointments.shared.domain.Error;

public class TimeRangeErrors {
    public static sadek.doctorAppointments.shared.domain.Error pastTimeRange = Error.failure("TimeRange.TimeInThePast", "time must be in the future");
    public static sadek.doctorAppointments.shared.domain.Error invalidTimeRange = Error.failure("TimeRange.InvalidTime", "time must be before end time and in same day");
}
