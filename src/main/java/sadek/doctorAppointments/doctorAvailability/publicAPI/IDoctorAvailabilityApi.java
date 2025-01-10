package sadek.doctorAppointments.doctorAvailability.publicAPI;

import org.springframework.stereotype.Service;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;
import java.util.UUID;

@Service
public interface IDoctorAvailabilityApi {
    Result<Void> releaseSlot(UUID slotId);
    Result<Void> reserveSlot(UUID slotId);
    Result<List<SlotDto>> getDoctorAvailableSlots(UUID doctorId);
    Result<SlotDto> getSlotById(UUID slotId);
}
