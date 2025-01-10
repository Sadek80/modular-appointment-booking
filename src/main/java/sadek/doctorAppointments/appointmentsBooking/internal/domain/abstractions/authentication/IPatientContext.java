package sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.authentication;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;
import sadek.doctorAppointments.shared.application.IUserContext;

import java.util.UUID;

@Component
public interface IPatientContext extends IUserContext<Patient, UUID> {
}
