package sadek.doctorAppointments.appointmentsBooking.internal.application.getAllPatientAppointments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.authentication.IPatientContext;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.repositories.IAppointmentListingRepository;
import sadek.doctorAppointments.shared.application.IQueryHandler;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPatientAppointmentQueryHandler implements IQueryHandler<GetAllPatientAppointmentsQuery, Result<Response<List<AppointmentResponseDto>>>> {
    private final IPatientContext patientContext;
    private final IAppointmentListingRepository appointmentRepository;

    @Override
    public Result<Response<List<AppointmentResponseDto>>> handle(GetAllPatientAppointmentsQuery query) {
        List<AppointmentResponseDto> result = appointmentRepository.getAllAppointments(patientContext.getUserId());

        return Result.success(
                Response.create(result)
        );
    }
}
