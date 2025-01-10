package sadek.doctorAppointments.appointmentsBooking.internal.application.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentUpdatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.domain.abstractions.IEventBus;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

@Component("DoctorAppointmentManagement-AppointmentUpdatedEventHandler")
@RequiredArgsConstructor
public class AppointmentUpdatedEventHandler {
    private final IEventBus eventBus;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentUpdatedEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentUpdatedDomainEvent event) {
        logger.info("Starting handle of AppointmentUpdatedDomainEvent: {}", event);

        eventBus.publishIntegrationEvent(new AppointmentUpdatedIntegrationEvent(
                event.appointmentId(),
                event.startTime(),
                event.endTime(),
                event.cost()
        ));

        logger.info("AppointmentUpdatedDomainEvent handled Successfully");
    }
}
