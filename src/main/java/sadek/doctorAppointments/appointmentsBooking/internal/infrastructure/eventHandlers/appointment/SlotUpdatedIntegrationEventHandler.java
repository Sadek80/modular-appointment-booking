package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.eventHandlers.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotUpdatedIntegrationEventHandler {
    private final IAppointmentRepository appointmentRepository;

    @Async
    @TransactionalEventListener
    @Transactional(value = AppointmentBookingConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public void handle(SlotUpdatedIntegrationEvent event) {
        log.info("Start Handling SlotUpdatedIntegrationEvent: {}", event);

        Appointment appointment = appointmentRepository.findBySlotId(event.slotId());

        if (appointment == null) {
            log.error("Slot {} not found", event.slotId());
            throw new AppointmentNotFoundException();
        }

        appointment.update(event.startTime(), event.endTime(), event.cost());

        appointmentRepository.save(appointment);

        log.info("SlotUpdatedIntegrationEvent successfully handled");
    }
}
