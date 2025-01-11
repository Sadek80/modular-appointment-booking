package sadek.doctorAppointments.appointmentsConfirmation.internal;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsConfirmation.publicAPI.IAppointmentConfirmationApi;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.domain.abstractions.ILogger;
import sadek.doctorAppointments.shared.domain.abstractions.ILoggerFactory;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentConfirmationApi implements IAppointmentConfirmationApi {
    private final ILoggerFactory loggerFactory;
    private ILogger logger;

    @PostConstruct
    private void initializeLogger() {
        this.logger = loggerFactory.getLogger(AppointmentConfirmationApi.class);
    }

    @Override
    public Result<Void> sendConfirmationToDoctor(UUID doctorId, String payload) {
        logger.warn(payload);

        return Result.success();
    }

    @Override
    public Result<Void> sendConfirmationToPatient(UUID patientId, String payload) {
        logger.warn(payload);

        return Result.success();
    }
}
