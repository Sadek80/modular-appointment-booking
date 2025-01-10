package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.shared.domain.Error;

public class SlotErrors {
    public static Error NOT_FOUND = Error.notFound("Slot.NotFound", "Slot is not found");
    public static Error UPDATE_TIME_VIOLATED = Error.failure("Slot.UpdateLocked", "Reserved Slot cannot be updated less than 2 hours before its start time");
    public static Error UPDATE_DATE_VIOLATED = Error.failure("Slot.UpdateLocked", "Change Reserved Slot start and end time day is not allowed. Please cancel appointment first.");
    public static Error OVERLAPPED = Error.conflict("Slot.Overlapped", "Slot overlaps with an existing slot");
    public static Error DURATION_EXCEEDED = Error.failure("Slot.DurationExceeded", "A single slot cannot exceed maximum hours duration");
    public static final Error TIME_DUE = Error.failure("Slot.TimeDue", "Slot time is passed");
    public static final Error ALREADY_RESERVED = Error.conflict("Slot.AlreadyReserved", "Slot is already reserved.");
}
