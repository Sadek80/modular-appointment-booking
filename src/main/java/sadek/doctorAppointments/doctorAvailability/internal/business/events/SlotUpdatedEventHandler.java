package sadek.doctorAppointments.doctorAvailability.internal.business.events;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAvailability.publicAPI.events.SlotUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.application.IEventBus;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class SlotUpdatedEventHandler {
    private final IEventBus eventBus;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(SlotUpdatedEventHandler.class);
    }

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
