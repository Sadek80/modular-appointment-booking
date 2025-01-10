package sadek.doctorAppointments.appointmentsBooking.internal.application.eventHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentUpdatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.domain.IEventBus;
import sadek.doctorAppointments.shared.domain.ILogger;

@Component
@RequiredArgsConstructor
public class AppointmentUpdatedEventHandler {
    private final ILogger logger;
    private final IEventBus eventBus;

    @Async
    @EventListener
    public void handle(AppointmentUpdatedDomainEvent event) {
        logger.info("Starting handle of AppointmentUpdatedDomainEvent: {}", event);

        eventBus.publishIntegrationEvent(new AppointmentUpdatedIntegrationEvent(
                event.startTime(),
                event.endTime(),
                event.cost()
        ));

        logger.info("AppointmentUpdatedDomainEvent handled Successfully");
    }
}
