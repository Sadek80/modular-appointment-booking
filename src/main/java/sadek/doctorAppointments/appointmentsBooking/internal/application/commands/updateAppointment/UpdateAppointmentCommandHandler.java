package sadek.doctorAppointments.appointmentsBooking.internal.application.commands.updateAppointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.AppointmentNotFoundException;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.config.AppointmentBookingConfig;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

@Service
@RequiredArgsConstructor
public class UpdateAppointmentCommandHandler implements ICommandHandler<UpdateAppointmentCommand, Result<Void>> {
    private final IAppointmentRepository appointmentRepository;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(UpdateAppointmentCommandHandler.class);
    }

    @Override
    @Transactional(value = AppointmentBookingConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Void> handle(UpdateAppointmentCommand updateAppointmentCommand) {
        logger.info("Start Handling UpdateAppointmentCommand: {}", updateAppointmentCommand);

        Appointment appointment = appointmentRepository.findBySlotId(updateAppointmentCommand.slotId());

        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

        appointment.update(updateAppointmentCommand.startTime(),
                updateAppointmentCommand.endTime(),
                updateAppointmentCommand.cost());

        appointmentRepository.save(appointment);

        logger.info("UpdateAppointmentCommand handled successfully");

        return Result.success();
    }
}
