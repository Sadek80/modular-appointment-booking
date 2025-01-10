package sadek.doctorAppointments.appointmentsBooking.internal.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BookAppointmentDto(@JsonProperty("slot_id") UUID slotId) {
}
