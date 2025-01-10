package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentUpdatedIntegrationEvent;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.UpdateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config.DoctorAppointmentManagementConfig;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class AppointmentUpdatedEventHandler {
    private final IAppointmentService appointmentService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentUpdatedEventHandler.class);
    }

    @Async
    @TransactionalEventListener
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
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
