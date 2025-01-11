package sadek.doctorAppointments.appointmentsBooking.internal.application.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCreatedDomainEvent;
import sadek.doctorAppointments.appointmentsConfirmation.publicAPI.IAppointmentConfirmationApi;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;
import sadek.doctorAppointments.shared.domain.Result;

@Service
@RequiredArgsConstructor
public class AppointmentCreatedConfirmationEventHandler {
    private final IAppointmentConfirmationApi appointmentConfirmationApi;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCreatedConfirmationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCreatedDomainEvent event) {
        logger.info("Starting handle of AppointmentCreatedDomainEvent: {}", event);

        String payload = "New appointment created, its details: " + event;

        try {
            Result<Void> result = appointmentConfirmationApi.sendConfirmationToDoctor(event.doctorId(), payload);

            if (result.isFailure()){
                logger.error("Failed to send confirmation to Doctor: {}", result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to send confirmation to Doctor", e);
        }

        logger.info("AppointmentCreatedDomainEvent handled Successfully");
    }}
