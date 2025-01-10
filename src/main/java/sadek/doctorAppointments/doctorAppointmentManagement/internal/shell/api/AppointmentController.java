package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CancelAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CompleteAppointmentDto;
import sadek.doctorAppointments.shared.presentation.BaseController;

@RestController("DoctorAppointmentManagement-AppointmentController")
@RequiredArgsConstructor
public class AppointmentController extends BaseController {
    private final IAppointmentService appointmentService;

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/doctors/appointments/complete"
    )
    public ResponseEntity<?> complete(@RequestBody CompleteAppointmentDto request) {
        return handleResult(appointmentService.completeAppointment(request), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/doctors/appointments/cancel"
    )
    public ResponseEntity<?> cancel(@RequestBody CancelAppointmentDto request) {
        return handleResult(appointmentService.cancelAppointment(request), HttpStatus.NO_CONTENT);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/doctors/appointments"
    )
    public ResponseEntity<?> getAllAppointments() {
        return handleResult(appointmentService.getAppointments(), HttpStatus.OK);
    }}
