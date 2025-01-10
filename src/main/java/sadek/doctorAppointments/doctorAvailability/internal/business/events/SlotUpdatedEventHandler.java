package sadek.doctorAppointments.doctorAvailability.internal.business.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAvailability.publicAPI.events.SlotUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.domain.IEventBus;
import sadek.doctorAppointments.shared.domain.ILogger;

@Service
@RequiredArgsConstructor
public class SlotUpdatedEventHandler {
    private final IEventBus eventBus;
    private final ILogger logger;


    @EventListener
    public void handle(SlotUpdatedEvent event) {
        logger.info("Start Handling updated event: " + event);

        eventBus.publishIntegrationEvent(new SlotUpdatedIntegrationEvent(
                event.slotId(),
                event.startTime(),
                event.endTime(),
                event.cost()
                ));

        logger.info("Slot Updated Event Published Successfully");
    }
}
