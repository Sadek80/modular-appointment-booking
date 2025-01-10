package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IPatientRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.patient.Patient;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.PatientEntity;
import sadek.doctorAppointments.shared.domain.abstractions.IEventBus;

@Repository
@RequiredArgsConstructor
public class PatientRepository implements IPatientRepository {
    private final IPatientJpaRepository patientJpaRepository;
    private final IEventBus eventBus;

    @Override
    public void save(Patient model) {
        PatientEntity patientEntity = PatientEntity.from(model);
        patientJpaRepository.save(patientEntity);
        model.occurredEvents().forEach(eventBus::publish);
    }
}