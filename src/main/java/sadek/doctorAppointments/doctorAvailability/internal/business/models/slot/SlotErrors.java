package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.shared.domain.Error;

public class SlotErrors {
    public static Error notFound = Error.notFound("Slot.NotFound", "Slot is not found");
    public static Error updateTimeViolated = Error.failure("Slot.UpdateLocked", "Reserved Slot cannot be updated less than 2 hours before its start time");
    public static Error updateDateViolated = Error.failure("Slot.UpdateLocked", "Change Reserved Slot start and end time day is not allowed. Please cancel appointment first.");
    public static Error overlapped = Error.conflict("Slot.Overlapped", "Slot overlaps with an existing slot");
    public static Error durationExceeded = Error.failure("Slot.Overlapped", "A single slot cannot exceed maximum hours duration");
    public static Error unSufficientCost = Error.failure("Slot.UnSufficientCost", "Slot cost must be greater than zero");
    public static Error pastTimeRange = Error.failure("Slot.TimeInThePast", "Slot time must be in the future");
    public static Error invalidTimeRange = Error.failure("Slot.InvalidTime", "Start time must be before end time and in same day");
}
