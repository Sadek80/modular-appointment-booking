package sadek.doctorAppointments.doctorAvailability.internal.business.models;

import sadek.doctorAppointments.shared.domain.Error;

public class SlotErrors {
    public static Error notFound = Error.notFound("Slot.NotFound", "Slot is not found");
    public static Error updateTimeViolated = Error.failure("Slot.UpdateLocked", "Reserved Slot cannot be updated less than 2 hours before its start time");
    public static Error updateDateViolated = Error.failure("Slot.UpdateLocked", "Change Reserved Slot start and end time day is not allowed. Please cancel appointment first.");
    public static Error overlapped = Error.conflict("Slot.Overlapped", "Slot overlaps with an existing slot");
    public static Error durationExceeded = Error.failure("Slot.Overlapped", "A single slot cannot exceed maximum hours duration");
}
