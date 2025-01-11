package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers.appointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.commands.updateAppointment.UpdateAppointmentCommand;
import sadek.doctorAppointments.doctorAvailability.publicAPI.events.SlotUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class SlotUpdatedIntegrationEventHandler {
    private final ICommandHandler<UpdateAppointmentCommand, Result<Void>> updateAppointmentCommandHandler;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(SlotUpdatedIntegrationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(SlotUpdatedIntegrationEvent event) {
        logger.info("Start Handling SlotUpdatedIntegrationEvent: {}", event);

        UpdateAppointmentCommand command = new UpdateAppointmentCommand(
          event.slotId(),
          event.startTime(),
          event.endTime(),
          event.cost()
        );

        try {
            Result<Void> result = updateAppointmentCommandHandler.handle(command);

            if (result.isFailure()){
                logger.error("Error handling SlotUpdatedIntegrationEvent: {}", result.getError());
            }
        }
        catch (Exception e) {
            logger.error("Error handling SlotUpdatedIntegrationEvent: {}", event, e);
        }

        logger.info("SlotUpdatedIntegrationEvent successfully handled");
    }
}
