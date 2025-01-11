package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.*;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.IAppointmentService;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.Appointment;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.models.appointment.AppointmentErrors;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.authentication.IDoctorContext;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.outputPorts.repositories.IAppointmentRepository;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.config.DoctorAppointmentManagementConfig;
import sadek.doctorAppointments.shared.domain.Response;
import sadek.doctorAppointments.shared.domain.Result;
import sadek.doctorAppointments.shared.application.IDateTimeProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final IAppointmentRepository appointmentRepository;
    private final IDoctorContext doctorContext;
    private final IDateTimeProvider dateTimeProvider;

    @Override
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER, propagation = Propagation.REQUIRES_NEW)
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
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER)
    public Result<Void> cancelAppointment(CancelAppointmentDto request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId());

        if (appointment == null) {
            return Result.failure(AppointmentErrors.NOT_FOUND);
        }

        appointment.cancel(dateTimeProvider.nowDateTime());
        appointmentRepository.save(appointment);

        return Result.success();
    }

    @Override
    @Transactional(value = DoctorAppointmentManagementConfig.TRANSACTION_MANAGER)
    public Result<Void> completeAppointment(CompleteAppointmentDto request) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId());

        if (appointment == null) {
            return Result.failure(AppointmentErrors.NOT_FOUND);
        }

        appointment.complete(dateTimeProvider.nowDateTime());
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
