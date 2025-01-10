package sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db;


import sadek.doctorAppointments.doctorAppointmentManagement.internal.core.inputPorts.dto.AppointmentResponseDto;
import sadek.doctorAppointments.doctorAppointmentManagement.internal.shell.db.entities.AppointmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMapper {

    public static AppointmentResponseDto toAppointmentResponseDto(AppointmentEntity appointment) {
        return new AppointmentResponseDto(
                appointment.getAppointmentId(),
                appointment.getDoctorId(),
                appointment.getPatientId(),
                appointment.getPatientName(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getReservedAt(),
                appointment.getCanceledAt(),
                appointment.getCompletedAt(),
                appointment.getCost(),
                appointment.getStatus().name()
        );
    }

    public static List<AppointmentResponseDto> toAppointmentResponseDtoList(List<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream()
                .map(AppointmentMapper::toAppointmentResponseDto)
                .collect(Collectors.toList());
    }
}
