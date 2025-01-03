package sadek.doctorAppointments.doctorAvailability.internal.business.dto;

import java.time.LocalDateTime;

public record CreateSlotDto(LocalDateTime startTime, LocalDateTime endTime, double cost) {
}
