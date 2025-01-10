package sadek.doctorAppointments.doctorAvailability.internal.business.models.slot;

import sadek.doctorAppointments.shared.domain.IValueObject;

import java.util.UUID;

public record SlotId(UUID value) implements IValueObject{
    public static SlotId generate() {
        return new SlotId(UUID.randomUUID());
    }

    public static SlotId fromString(String slotId) {
        return new SlotId(UUID.fromString(slotId));
    }
}
