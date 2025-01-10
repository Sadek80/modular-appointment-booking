package sadek.doctorAppointments.doctorAppointmentManagement.core.outputPorts.repositories;

import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;
import sadek.doctorAppointments.shared.domain.abstractions.IBaseRepository;

@Repository
public interface IPatientRepository extends IBaseRepository<Patient> {
    @Override
    void save(Patient entity);
}
