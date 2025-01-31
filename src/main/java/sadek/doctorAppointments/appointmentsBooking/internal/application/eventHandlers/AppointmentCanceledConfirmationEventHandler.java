package sadek.doctorAppointments.appointmentsBooking.internal.application.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services.IAppointmentConfirmationService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.events.AppointmentCanceledDomainEvent;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;
import sadek.doctorAppointments.shared.domain.Result;

@Service
@RequiredArgsConstructor
public class AppointmentCanceledConfirmationEventHandler {
    private final IAppointmentConfirmationService appointmentConfirmationService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCreatedConfirmationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCanceledDomainEvent event){
        logger.info("Starting handle of AppointmentCanceledDomainEvent: {}", event);

        String payload = "Appointment with Id: " + event.appointmentId() + "has been canceled by doctor";

        try {
            Result<Void> result = appointmentConfirmationService.sendConfirmationToDoctor(event.patientId(), payload);

            if (result.isFailure()){
                logger.error("Failed to send confirmation to Patient: {}", result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to send confirmation to Patient", e);
        }

        logger.info("AppointmentCanceledDomainEvent handled Successfully");

    }
}
