package sadek.doctorAppointments.appointmentsBooking.internal.application.commands.bookAppointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.authentication.IPatientContext;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.services.AppointmentOverlappingService;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.config.AppointmentBookingConfig;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.domain.abstractions.IDateTimeProvider;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookAppointmentCommandHandler implements ICommandHandler<BookAppointmentCommand, Result<Response<UUID>>> {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorAvailabilityApi doctorAvailabilityApi;
    private final IPatientContext patientContext;
    private final IDateTimeProvider dateTimeProvider;
    private final AppointmentOverlappingService appointmentOverlappingService;
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(BookAppointmentCommandHandler.class);
    }

    @Override
    @Transactional(value = AppointmentBookingConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Response<UUID>> handle(BookAppointmentCommand command) {
        logger.info("Start Handling command: {}", command);

        Result<SlotDto> slotDtoResult = doctorAvailabilityApi.getSlotById(command.slotId());

        if (slotDtoResult.isFailure()){
            return Result.failure(slotDtoResult.getError());
        }

        SlotDto slotDto = slotDtoResult.getValue();

        TimeRange timeRange = new TimeRange(slotDto.startTime(), slotDto.endTime());
        appointmentOverlappingService.validateNoOverlappingAppointments(patientContext.getUserId(), timeRange);

        Appointment newAppointment = Appointment.book(
                slotDtoResult.getValue(),
                patientContext.getUser(),
                dateTimeProvider.nowDateTime());

        doctorAvailabilityApi.reserveSlot(command.slotId());

        appointmentRepository.save(newAppointment);

        logger.info("Command handled successfully");

        return Result.success(
                Response.create(newAppointment.getId().value())
        );
    }
}
