package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers.appointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.AppointmentNotFoundException;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.config.AppointmentBookingConfig;
import sadek.doctorAppointments.doctorAvailability.publicAPI.events.SlotUpdatedIntegrationEvent;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class SlotUpdatedIntegrationEventHandler {
    private final IAppointmentRepository appointmentRepository;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(SlotUpdatedIntegrationEventHandler.class);
    }

    @Async
    @TransactionalEventListener
    @Transactional(value = AppointmentBookingConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public void handle(SlotUpdatedIntegrationEvent event) {
        logger.info("Start Handling SlotUpdatedIntegrationEvent: {}", event);

        Appointment appointment = appointmentRepository.findBySlotId(event.slotId());

        if (appointment == null) {
            logger.error("Slot {} not found", event.slotId());
            throw new AppointmentNotFoundException();
        }

        appointment.update(event.startTime(), event.endTime(), event.cost());

        appointmentRepository.save(appointment);

        logger.info("SlotUpdatedIntegrationEvent successfully handled");
    }
}
