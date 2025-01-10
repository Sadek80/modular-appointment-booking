package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts;

import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.*;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

public interface IAppointmentService {
    Result<Void> createAppointment(CreateAppointmentDto request);
    Result<Void> updateAppointment(UpdateAppointmentDto request);
    Result<Void> cancelAppointment(CancelAppointmentDto request);
    Result<Void> completeAppointment(CompleteAppointmentDto request);
    Result<Response<List<AppointmentResponseDto>>> getAppointments();
}
