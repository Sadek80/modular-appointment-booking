package sadek.doctorAppointments.appointmentsConfirmation.publicAPI;

import sadek.doctorAppointments.shared.domain.Result;

import java.util.UUID;

public interface IAppointmentConfirmationApi {
    Result<Void> sendConfirmationToDoctor(UUID doctorId, String payload);
    Result<Void> sendConfirmationToPatient(UUID patientId, String payload);
}
