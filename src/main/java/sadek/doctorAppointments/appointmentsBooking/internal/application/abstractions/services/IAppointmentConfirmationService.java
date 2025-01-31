package sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services;

import sadek.doctorAppointments.shared.domain.Result;

import java.util.UUID;

public interface IAppointmentConfirmationService {
    Result<Void> sendConfirmationToDoctor(UUID doctorId, String payload);
    Result<Void> sendConfirmationToPatient(UUID patientId, String payload);
}
