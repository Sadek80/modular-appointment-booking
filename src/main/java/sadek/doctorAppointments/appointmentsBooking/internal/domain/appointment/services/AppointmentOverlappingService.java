package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.abstractions.repositories.IAppointmentRepository;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentErrors;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.AppointmentStatus;
import sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions.AppointmentRuleViolation;
import sadek.doctorAppointments.shared.domain.valueObject.TimeRange;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentOverlappingService {
    private final IAppointmentRepository appointmentRepository;

    public void validateNoOverlappingAppointments(UUID userId, TimeRange timeRange) {
        boolean isOverlapping = appointmentRepository.isBookingOverlapping(userId, timeRange.startTime(), timeRange.endTime(), AppointmentStatus.BOOKED);

        if (isOverlapping) {
            throw new AppointmentRuleViolation(AppointmentErrors.OVERLAPPED);
        }
    }
}
