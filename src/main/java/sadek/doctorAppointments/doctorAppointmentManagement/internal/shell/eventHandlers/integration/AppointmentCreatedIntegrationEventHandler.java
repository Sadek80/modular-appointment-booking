package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.eventHandlers.integration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentCreatedIntegrationEvent;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CreateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class AppointmentCreatedIntegrationEventHandler {
    private final IAppointmentService appointmentService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCreatedIntegrationEventHandler.class);
    }

    @Async
    @EventListener
    public void handle(AppointmentCreatedIntegrationEvent event) {
        logger.info("Start Handling AppointmentCreatedIntegrationEvent: {}", event);

        try {
            Result<Void> result =  appointmentService.createAppointment(new CreateAppointmentDto(
                    event.appointmentId(),
                    event.startTime(),
                    event.endTime(),
                    event.cost(),
                    event.patientId(),
                    event.patientName(),
                    event.doctorId(),
                    event.reservedAt()
            ));

            if (result.isFailure()){
                logger.error("Failed to create appointment with id {}. caused by: {}", event.appointmentId(), result.getError());
            }

        } catch (Exception e) {
            logger.error("Failed to handle AppointmentCreatedIntegrationEvent", e);
        }

        logger.info("AppointmentCreatedIntegrationEvent handled successfully");
    }
}
