package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.IAppointmentConfirmationService;
import sadek.doctorAppointments.appointmentsConfirmation.publicAPI.IAppointmentConfirmationApi;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentConfirmationService implements IAppointmentConfirmationService {
    private final IAppointmentConfirmationApi appointmentConfirmationApi;

    @Override
    public Result<Void> sendConfirmationToDoctor(UUID doctorId, String payload) {
        return appointmentConfirmationApi.sendConfirmationToDoctor(doctorId, payload);
    }

    @Override
    public Result<Void> sendConfirmationToPatient(UUID patientId, String payload) {
        return appointmentConfirmationApi.sendConfirmationToPatient(patientId, payload);
    }
}
