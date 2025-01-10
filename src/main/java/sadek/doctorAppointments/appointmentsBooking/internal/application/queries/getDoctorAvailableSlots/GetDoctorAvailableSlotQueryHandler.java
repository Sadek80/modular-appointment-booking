package sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getDoctorAvailableSlots;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;
import sadek.doctorAppointments.shared.application.IQueryHandler;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDoctorAvailableSlotQueryHandler implements IQueryHandler<GetDoctorAvailableSlotQuery, Result<Response<List<SlotDto>>>> {
    private final IDoctorAvailabilityApi doctorAvailabilityApi;

    @Override
    public Result<Response<List<SlotDto>>> handle(GetDoctorAvailableSlotQuery query) {
        Result<List<SlotDto>> result = doctorAvailabilityApi.getDoctorAvailableSlots(query.doctorId());

        if (result.isFailure()) {
            return Result.failure(result.getError());
        }

        return Result.success(
                Response.create(result.getValue())
        );
    }
}
