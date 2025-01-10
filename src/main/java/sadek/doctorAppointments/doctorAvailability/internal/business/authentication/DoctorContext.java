package sadek.doctorAppointments.doctorAvailability.internal.business.authentication;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.doctorAvailability.internal.business.models.doctor.Doctor;
import sadek.doctorAppointments.shared.application.IUserContext;

import java.util.UUID;

@Component
public class DoctorContext implements IUserContext<Doctor, UUID> {
    private static final String DOCTOR_ID = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";
    private static final String DOCTOR_NAME = "sadek2";
    private static final Doctor DEFAULT_DOCTOR =  Doctor.create(DOCTOR_ID, DOCTOR_NAME);

    @Override
    public Doctor getUser() {
        return DEFAULT_DOCTOR;
    }

    @Override
    public UUID getUserId() {
        return DEFAULT_DOCTOR.getId().value();
    }
}
