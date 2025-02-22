package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers.confirmation;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services.IAppointmentConfirmationService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCreatedDomainEvent;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;
import sadek.doctorAppointments.shared.domain.Result;

@Service
@RequiredArgsConstructor
public class AppointmentCreatedConfirmationEventHandler {
    private final IAppointmentConfirmationService appointmentConfirmationService;
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

        sendConformationToDoctor(event);
        sendConformationToPatient(event);

        logger.info("AppointmentCreatedDomainEvent handled Successfully");
    }

    private void sendConformationToDoctor(AppointmentCreatedDomainEvent event) {
        try {
            String payload = "New appointment created, its details: " + event;
            Result<Void> result = appointmentConfirmationService.sendConfirmationToDoctor(event.doctorId(), payload);

            if (result.isFailure()){
                logger.error("Failed to send confirmation to Doctor: {}", result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to send confirmation to Doctor", e);
        }
    }

    private void sendConformationToPatient(AppointmentCreatedDomainEvent event) {
        try {
            String payload = "New appointment created, its details: " + event;
            Result<Void> result = appointmentConfirmationService.sendConfirmationToPatient(event.patientId(), payload);

            if (result.isFailure()){
                logger.error("Failed to send confirmation to Patient: {}", result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to send confirmation to Patient", e);
        }
    }
}
