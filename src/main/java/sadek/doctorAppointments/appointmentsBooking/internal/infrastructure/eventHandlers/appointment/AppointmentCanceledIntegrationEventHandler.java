package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers.appointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.commands.cancelAppointment.CancelAppointmentCommand;
import sadek.doctorAppointments.doctorAppointmentManagement.publicAPI.events.AppointmentCanceledIntegrationEvent;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class AppointmentCanceledIntegrationEventHandler {
    private final ICommandHandler<CancelAppointmentCommand, Result<Void>> cancelAppointmentCommandHandler;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCanceledIntegrationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCanceledIntegrationEvent event) {
        logger.info("Start Handling AppointmentCanceledIntegrationEvent: {}", event);

        CancelAppointmentCommand command = new CancelAppointmentCommand(
          event.appointmentId(),
          event.canceledAt()
        );

        try {
            Result<Void> result = cancelAppointmentCommandHandler.handle(command);

            if (result.isFailure()){
                logger.error("Error handling AppointmentCanceledIntegrationEvent: {}", result.getError());
            }
        }
        catch (Exception e) {
            logger.error("Error handling AppointmentCanceledIntegrationEvent: {}", event, e);
        }

        logger.info("AppointmentCanceledIntegrationEvent handled successfully");
    }
}
