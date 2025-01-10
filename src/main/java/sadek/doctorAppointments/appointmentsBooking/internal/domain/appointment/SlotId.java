package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment;

import sadek.doctorAppointments.shared.domain.abstractions.IValueObject;

import java.util.UUID;

public record SlotId(UUID value) implements IValueObject {
    public static SlotId generate() {
        return new SlotId(UUID.randomUUID());
    }

    public static SlotId fromString(String slotId) {
        return new SlotId(UUID.fromString(slotId));
    }

    public static SlotId from(UUID slotId) {
        return new SlotId(slotId);
    }
}
