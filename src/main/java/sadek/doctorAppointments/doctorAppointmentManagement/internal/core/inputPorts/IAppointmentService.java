package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts;

import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CreateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.UpdateAppointmentDto;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

public interface IAppointmentService {
    Result<Void> createAppointment(CreateAppointmentDto request);
    Result<Void> updateAppointment(UpdateAppointmentDto request);
    Result<Response<List<AppointmentResponseDto>>> getAppointments();
}
