package sadek.doctorAppointments.appointmentsBooking.internal.application.bookAppointment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.appointmentsBooking.internal.application.AppointmentBookingApplicationConfig;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.authentication.IPatientContext;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.IDoctorAvailabilityService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.Appointment;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.services.AppointmentOverlappingService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.dto.SlotInfoDto;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.application.IDateTimeProvider;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.ILoggerFactory;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookAppointmentCommandHandler implements ICommandHandler<BookAppointmentCommand, Result<Response<UUID>>> {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorAvailabilityService doctorAvailabilityService;
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
    @Transactional(value = AppointmentBookingApplicationConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
    public Result<Response<UUID>> handle(BookAppointmentCommand command) {
        logger.info("Start Handling command: {}", command);

        Result<SlotInfoDto> slotDtoResult = doctorAvailabilityService.getSlotById(command.slotId());
        SlotInfoDto slotInfoDto = slotDtoResult.getValue();

        TimeRange timeRange = new TimeRange(slotInfoDto.startTime(), slotInfoDto.endTime());
        appointmentOverlappingService.validateNoOverlappingAppointments(patientContext.getUserId(), timeRange);

        Appointment newAppointment = Appointment.book(
                slotInfoDto,
                patientContext.getUser(),
                dateTimeProvider.nowDateTime());

        doctorAvailabilityService.reserveSlot(command.slotId());

        try {
            appointmentRepository.save(newAppointment);
            logger.info("Command handled successfully");

        } catch (Exception e) {
            doctorAvailabilityService.releaseSlot(command.slotId());
        }

        return Result.success(
                Response.create(newAppointment.getId().value())
        );
    }
}
