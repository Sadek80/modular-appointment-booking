package sadek.doctorAppointments.doctorAvailability.publicAPI;

import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

public interface IDoctorAvailabilityApi {
    Result<Void> releaseSlot(UUID slotId);
    Result<Void> reserveSlot(UUID slotId);
    Result<List<SlotDto>> getDoctorAvailableSlots(UUID doctorId);
}
