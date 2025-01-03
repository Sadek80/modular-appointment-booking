package sadek.doctorAppointments.doctorAvailability.publicAPI.events;

import sadek.doctorAppointments.shared.domain.IDomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotUpdatedEvent(UUID slotId,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               double cost)
        implements IDomainEvent {
}
