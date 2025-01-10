package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.eventHandlers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import sadek.doctorAppointments.appointmentsBooking.publicAPI.events.AppointmentCreatedIntegrationEvent;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CreateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config.DoctorAppointmentManagementConfig;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

@Component
@RequiredArgsConstructor
public class AppointmentCreatedEventHandler {
    private final IAppointmentService appointmentService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentCreatedEventHandler.class);
    }

    @Async
    @TransactionalEventListener
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
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