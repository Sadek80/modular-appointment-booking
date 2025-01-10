package sadek.doctorAppointments.doctorAppointmentManagement.core.inputPorts;

import sadek.doctorAppointments.doctorAppointmentManagement.core.dto.AppointmentResponseDto;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

public interface IAppointmentService {
    Result<Void> createAppointment();
    Result<Void> updateAppointment();
    Result<Response<List<AppointmentResponseDto>>> getAppointments();
}