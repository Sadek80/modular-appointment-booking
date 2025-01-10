package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.CreateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.UpdateAppointmentDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.Appointment;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.AppointmentErrors;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.authentication.IDoctorContext;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.repositories.IAppointmentRepository;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorContext doctorContext;

    @Override
    @Transactional
    public Result<Void> createAppointment(CreateAppointmentDto request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId());

        if (appointment != null) {
            return Result.failure(AppointmentErrors.ALREADY_EXISTS);
        }

        Appointment newAppointment = Appointment.create(
                request.appointmentId(),
                request.patientId(),
                request.patientName(),
                request.doctorId(),
                request.startTime(),
                request.endTime(),
                request.cost(),
                request.reservedAt()
        );

        appointmentRepository.save(newAppointment);

        return Result.success();
    }

    @Override
    @Transactional
    public Result<Void> updateAppointment(UpdateAppointmentDto request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId());

        if (appointment == null) {
            return Result.failure(AppointmentErrors.NOT_FOUND);
        }

        appointment.update(request.startTime(), request.endTime(), request.cost());
        appointmentRepository.save(appointment);

        return Result.success();
    }

    @Override
    public Result<Response<List<AppointmentResponseDto>>> getAppointments() {
        List<AppointmentResponseDto> response = appointmentRepository.getAllAppointments(doctorContext.getUserId());

        return Result.success(
                Response.create(response)
        );
    }
}
