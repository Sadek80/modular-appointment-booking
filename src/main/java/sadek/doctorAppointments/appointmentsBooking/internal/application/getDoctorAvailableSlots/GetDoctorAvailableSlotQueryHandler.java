package sadek.doctorAppointments.appointmentsBooking.internal.application.getDoctorAvailableSlots;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.IDoctorAvailabilityService;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.dto.SlotInfoDto;
import sadek.doctorAppointments.shared.application.IQueryHandler;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetDoctorAvailableSlotQueryHandler implements IQueryHandler<GetDoctorAvailableSlotQuery, Result<Response<List<SlotInfoDto>>>> {
    private final IDoctorAvailabilityService doctorAvailabilityService;

    @Override
    public Result<Response<List<SlotInfoDto>>> handle(GetDoctorAvailableSlotQuery query) {
        Result<List<SlotInfoDto>> result = doctorAvailabilityService.getDoctorAvailableSlots(query.doctorId());

        return Result.success(
                Response.create(result.getValue())
        );
    }
}
