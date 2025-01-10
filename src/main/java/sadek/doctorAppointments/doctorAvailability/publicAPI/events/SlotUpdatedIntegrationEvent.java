package sadek.doctorAppointments.doctorAvailability.publicAPI.events;

import sadek.doctorAppointments.shared.domain.IIntegrationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlotUpdatedIntegrationEvent(UUID slotId,
                                          LocalDateTime startTime,
                                          LocalDateTime endTime,
                                          double cost)
        implements IIntegrationEvent {
}