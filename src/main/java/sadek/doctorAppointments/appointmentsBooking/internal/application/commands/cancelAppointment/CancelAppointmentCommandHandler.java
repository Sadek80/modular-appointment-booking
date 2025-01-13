package sadek.doctorAppointments.appointmentsBooking.internal.application.commands.cancelAppointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services.IDoctorAvailabilityService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.AppointmentNotFoundException;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.config.AppointmentBookingConfig;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.IDateTimeProvider;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class CancelAppointmentCommandHandler implements ICommandHandler<CancelAppointmentCommand, Result<Void>> {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorAvailabilityService doctorAvailabilityService;
    private final IDateTimeProvider dateTimeProvider;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(CancelAppointmentCommandHandler.class);
    }

    @Override
    @Transactional(value = AppointmentBookingConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Void> handle(CancelAppointmentCommand cancelAppointmentCommand) {
        logger.info("Start Handling CancelAppointmentCommand: {}", cancelAppointmentCommand);

        Appointment appointment = appointmentRepository.findById(cancelAppointmentCommand.appointmentId());

        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        appointment.cancel(cancelAppointmentCommand.canceledAt(), dateTimeProvider.nowDateTime());

        doctorAvailabilityService.releaseSlot(appointment.getSlotId().value());

        appointmentRepository.save(appointment);

        logger.info("CancelAppointmentCommand handled successfully");

        return Result.success();
    }
}
