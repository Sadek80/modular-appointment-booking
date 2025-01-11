package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.events;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAppointmentManagement.publicAPI.events.AppointmentCanceledIntegrationEvent;
import sadek.doctorAppointments.shared.application.IEventBus;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class AppointmentCanceledDomainEventHandler {
    private final IEventBus eventBus;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCanceledDomainEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCanceledDomainEvent event) {
        logger.info("Start handling AppointmentCanceledDomainEvent: {}", event);

        eventBus.publishIntegrationEvent(new AppointmentCanceledIntegrationEvent(
                event.appointmentId(),
                event.canceledAt()
        ));

        logger.info("AppointmentCanceledDomainEvent handled successfully");
    }
}
