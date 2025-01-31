package sadek.doctorAppointments.appointmentsBooking.internal.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sadek.doctorAppointments.appointmentsBooking.internal.api.dto.BookAppointmentDto;
import sadek.doctorAppointments.appointmentsBooking.internal.application.bookAppointment.BookAppointmentCommand;
import sadek.doctorAppointments.appointmentsBooking.internal.application.getAllPatientAppointments.AppointmentResponseDto;
import sadek.doctorAppointments.appointmentsBooking.internal.application.getAllPatientAppointments.GetAllPatientAppointmentsQuery;
import sadek.doctorAppointments.appointmentsBooking.internal.application.getDoctorAvailableSlots.GetDoctorAvailableSlotQuery;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.services.dto.SlotInfoDto;
import sadek.doctorAppointments.shared.application.ICommandHandler;
import sadek.doctorAppointments.shared.application.IQueryHandler;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.presentation.BaseController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppointmentController extends BaseController {
    private final ICommandHandler<BookAppointmentCommand, Result<Response<UUID>>> bookAppointmentCommandHandler;
    private final IQueryHandler<GetDoctorAvailableSlotQuery, Result<Response<List<SlotInfoDto>>>> getDoctorAvailableSlotQueryHandler;
    private final IQueryHandler<GetAllPatientAppointmentsQuery, Result<Response<List<AppointmentResponseDto>>>>  getAllPatientAppointmentsQueryHandler;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/appointments"
    )
    public ResponseEntity<?> addSlot(@RequestBody BookAppointmentDto request) {
        BookAppointmentCommand bookAppointmentCommand = new BookAppointmentCommand(request.slotId());
        return handleResult(bookAppointmentCommandHandler.handle(bookAppointmentCommand), HttpStatus.CREATED);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/appointments/slots/{doctorId}"
    )
    public ResponseEntity<?> getAllDoctorSlots(@PathVariable UUID doctorId) {
        return handleResult(getDoctorAvailableSlotQueryHandler.handle(new GetDoctorAvailableSlotQuery(doctorId)), HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/appointments"
    )
    public ResponseEntity<?> getAllAppointments() {
        return handleResult(getAllPatientAppointmentsQueryHandler.handle(new GetAllPatientAppointmentsQuery()), HttpStatus.OK);
    }
}
