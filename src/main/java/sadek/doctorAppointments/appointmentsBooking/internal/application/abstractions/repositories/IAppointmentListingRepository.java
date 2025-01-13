package sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.repositories;

import sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments.AppointmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface IAppointmentListingRepository {
    List<AppointmentResponseDto> getAllAppointments(UUID patientId);
}
