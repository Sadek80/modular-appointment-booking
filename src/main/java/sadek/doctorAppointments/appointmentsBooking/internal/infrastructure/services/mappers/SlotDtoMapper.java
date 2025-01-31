package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.services.mappers;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.dto.SlotInfoDto;
import sadek.doctorAppointments.doctorAvailability.publicAPI.SlotDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlotDtoMapper {

    public SlotInfoDto mapToSlotInfoDto(SlotDto slot) {
        return new SlotInfoDto(
                slot.slotId(),
                slot.doctorId(),
                slot.doctorName(),
                slot.startTime(),
                slot.endTime(),
                slot.cost()
        );
    }

    public List<SlotInfoDto> mapToSlotInfoDtoList(List<SlotDto> slots) {
        return slots.stream().map(this::mapToSlotInfoDto).collect(Collectors.toList());
    }
}
