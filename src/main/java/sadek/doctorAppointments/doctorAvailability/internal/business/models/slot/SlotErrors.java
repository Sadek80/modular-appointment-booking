package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.shared.domain.Error;

public class SlotErrors {
    public static Error notFound = Error.notFound("Slot.NotFound", "Slot is not found");
}
