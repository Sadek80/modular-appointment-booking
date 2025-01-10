package sadek.doctorAppointments.appointmentsBooking.internal.application.queries.getDoctorAvailableSlots;

import sadek.doctorAppointments.shared.application.IQuery;

import java.util.UUID;

public record GetDoctorAvailableSlotQuery(UUID doctorId) implements IQuery {
}
