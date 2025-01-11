package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCreatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentCreatedIntegrationEvent;
import sadek.doctorAppointments.shared.application.IEventBus;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Component("DoctorAppointmentManagement-AppointmentCreatedEventHandler")
@RequiredArgsConstructor
public class AppointmentCreatedDomainEventHandler {
    private final IEventBus eventBus;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCreatedDomainEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCreatedDomainEvent event) {
        logger.info("Starting handle of AppointmentCreatedDomainEvent: {}", event);

        eventBus.publishIntegrationEvent(new AppointmentCreatedIntegrationEvent(
                event.appointmentId(),
                event.startTime(),
                event.endTime(),
                event.cost(),
                event.patientId(),
                event.patientName(),
                event.doctorId(),
                event.reservedAt()
        ));

        logger.info("AppointmentCreatedDomainEvent handled Successfully");
    }
}
