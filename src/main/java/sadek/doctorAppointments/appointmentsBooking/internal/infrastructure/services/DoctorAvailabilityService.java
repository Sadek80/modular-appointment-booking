package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services.IDoctorAvailabilityService;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services.dto.SlotInfoDto;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services.mappers.SlotDtoMapper;
import sadek.doctorAppointments.doctorAvailability.publicAPI.IDoctorAvailabilityApi;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;
import sadek.doctorAppointments.shared.application.exceptions.AppException;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService implements IDoctorAvailabilityService {
    private final IDoctorAvailabilityApi doctorAvailabilityApi;
    private final SlotDtoMapper slotMapper;

    @Override
    public Result<Void> releaseSlot(UUID slotId) {
        Result<Void> result = doctorAvailabilityApi.releaseSlot(slotId);

        if (result.isFailure()) {
            throw new AppException.IntegrationException(result.getError());
        }

        return result;
    }

    @Override
    public Result<Void> reserveSlot(UUID slotId) {
        Result<Void> result = doctorAvailabilityApi.reserveSlot(slotId);

        if (result.isFailure()) {
            throw new AppException.IntegrationException(result.getError());
        }

        return result;
    }

    @Override
    public Result<List<SlotInfoDto>> getDoctorAvailableSlots(UUID doctorId) {
        Result<List<SlotDto>> result = doctorAvailabilityApi.getDoctorAvailableSlots(doctorId);

        if (result.isFailure()){
            throw new AppException.IntegrationException(result.getError());
        }

        return Result.success(slotMapper.mapToSlotInfoDtoList(result.getValue()));
    }

    @Override
    public Result<SlotInfoDto> getSlotById(UUID slotId) {
        Result<SlotDto> result = doctorAvailabilityApi.getSlotById(slotId);

        if (result.isFailure()){
            throw new AppException.IntegrationException(result.getError());
        }

        return Result.success(slotMapper.mapToSlotInfoDto(result.getValue()));
    }
}
