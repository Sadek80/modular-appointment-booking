package sadek.doctorAppointments.doctorAvailability.internal.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sadek.doctorAppointments.doctorAvailability.internal.business.dto.CreateSlotDto;
import sadek.doctorAppointments.doctorAvailability.internal.business.services.SlotService;
import sadek.doctorAppointments.shared.presentation.BaseController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SlotController extends BaseController {
    private final SlotService slotService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/doctors/availability"
    )
    public ResponseEntity<?> addSlot(@RequestBody CreateSlotDto request) {
        return handleResult(slotService.createSlot(request), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/doctors/availability"
    )
    public ResponseEntity<?> getAllSlots() {
        return handleResult(slotService.getAllSlots());
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/doctors/availability/{id}"
    )
    public ResponseEntity<?> updateSlot(@PathVariable UUID id, @RequestBody CreateSlotDto request) {
        return handleResult(slotService.updateSlot(id, request), HttpStatus.NO_CONTENT);
    }
}
