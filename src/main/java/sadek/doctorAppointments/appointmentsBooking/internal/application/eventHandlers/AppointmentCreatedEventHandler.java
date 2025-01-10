package sadek.doctorAppointments.appointmentsBooking.internal.application.eventHandlers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCreatedDomainEvent;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentCreatedIntegrationEvent;
import sadek.doctorAppointments.shared.domain.IEventBus;
import sadek.doctorAppointments.shared.domain.ILogger;

@Component
@RequiredArgsConstructor
public class AppointmentCreatedEventHandler {
    private final ILogger logger;
    private final IEventBus eventBus;

    @Async
    @EventListener
    public void handle(AppointmentCreatedDomainEvent event) {
        logger.info("Starting handle of AppointmentCreatedDomainEvent: {}", event);

        eventBus.publishIntegrationEvent(new AppointmentCreatedIntegrationEvent(
                event.appointmentId(),
                event.slotId(),
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
