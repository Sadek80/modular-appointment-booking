package sadek.doctorAppointments.doctorAvailability.publicAPI;

import sadek.doctorAppointments.shared.domain.Result;

import java.util.UUID;

public interface IDoctorAvailabilityApi {
    Result<Void> releaseSlot(UUID slotId);
}
