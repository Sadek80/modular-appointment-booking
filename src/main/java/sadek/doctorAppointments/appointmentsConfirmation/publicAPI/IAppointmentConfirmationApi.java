package sadek.doctorAppointments.appointmentsConfirmation.publicAPI;

import sadek.doctorAppointments.shared.domain.Result;

import java.util.UUID;

public interface IAppointmentConfirmationApi {
    public Result<Void> sendConfirmationToDoctor(UUID doctorId, String payload);
    public Result<Void> sendConfirmationToPatient(UUID patientId, String payload);
}
