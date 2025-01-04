package sadek.doctorAppointments.doctorAvailability.internal.business.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAvailability.internal.business.services.SlotService;
import sadek.doctorAppointments.shared.domain.ILogger;

@Service
@RequiredArgsConstructor
public class SlotUpdatedEventHandler {
    private final SlotService slotService;
    private final ILogger logger;

    @Async
    @EventListener
    public void handle(SlotUpdatedEvent event) {
        logger.warn("Start Slot updated event: " + event);

    }
}
