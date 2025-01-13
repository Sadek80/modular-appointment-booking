package sadek.doctorAppointments.appointmentsBooking.internal.application.abstractions.services;

import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services.dto.SlotInfoDto;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

public interface IDoctorAvailabilityService {
    Result<Void> releaseSlot(UUID slotId);
    Result<Void> reserveSlot(UUID slotId);
    Result<List<SlotInfoDto>> getDoctorAvailableSlots(UUID doctorId);
    Result<SlotInfoDto> getSlotById(UUID slotId);
}
