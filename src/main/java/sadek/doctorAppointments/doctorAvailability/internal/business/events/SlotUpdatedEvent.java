package sadek.doctorAppointments.doctorAvailability.internal.business.events;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotUpdatedEvent(UUID slotId,
                               LocalDateTime startTime,
                               LocalDateTime endTime,
                               double cost)
        implements IDomainEvent {
}
