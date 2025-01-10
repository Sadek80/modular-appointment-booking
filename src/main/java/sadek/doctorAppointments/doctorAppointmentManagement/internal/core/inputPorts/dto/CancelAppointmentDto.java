package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto;

import java.util.UUID;

public record CancelAppointmentDto (
        UUID appointmentId
) {
}
