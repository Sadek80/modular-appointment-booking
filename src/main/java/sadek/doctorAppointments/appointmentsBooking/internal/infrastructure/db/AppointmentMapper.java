package sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db;

import sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getAllPatientAppointments.AppointmentResponseDto;
import sadek.doctorAppointments.appointmentsBooking.internal.infrastructure.db.entities.AppointmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMapper {

    public static AppointmentResponseDto toAppointmentResponseDto(AppointmentEntity appointment) {
        return new AppointmentResponseDto(
                appointment.getDoctorId(),
                appointment.getDoctorName(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getReservedAt(),
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
