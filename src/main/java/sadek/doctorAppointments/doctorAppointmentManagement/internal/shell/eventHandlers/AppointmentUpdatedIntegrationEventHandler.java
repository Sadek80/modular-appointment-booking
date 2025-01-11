package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentUpdatedIntegrationEvent;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.UpdateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class AppointmentUpdatedIntegrationEventHandler {
    private final IAppointmentService appointmentService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentUpdatedIntegrationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentUpdatedIntegrationEvent event) {
        logger.info("Start Handling AppointmentUpdatedEventHandler: {}", event);

        try {
            Result<Void> result =  appointmentService.updateAppointment(new UpdateAppointmentDto(
                    event.appointmentId(),
                    event.startTime(),
                    event.endTime(),
                    event.cost()
            ));

            if (result.isFailure()){
                logger.error("Failed to create appointment with id {}. caused by: {}", event.appointmentId(), result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to handle AppointmentUpdatedEventHandler", e);
        }

        logger.info("AppointmentUpdatedEventHandler handled successfully");

    }
}
