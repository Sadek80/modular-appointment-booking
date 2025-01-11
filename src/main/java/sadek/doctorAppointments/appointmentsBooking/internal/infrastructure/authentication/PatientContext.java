package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.authentication;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.authentication.IPatientContext;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;

import java.util.UUID;

@Component
public class PatientContext implements IPatientContext {
    private static final String PATIENT_ID = "018b2f19-e79e-7d6a-a56d-29feb6211b04";
    private static final String PATIENT_NAME = "sadek";
    private static final Patient DEFAULT_PATIENT =  Patient.create(PATIENT_ID, PATIENT_NAME);

    @Override
    public Patient getUser() {
        return DEFAULT_PATIENT;
    }

    @Override
    public UUID getUserId() {
        return DEFAULT_PATIENT.getId().value();
    }
}
