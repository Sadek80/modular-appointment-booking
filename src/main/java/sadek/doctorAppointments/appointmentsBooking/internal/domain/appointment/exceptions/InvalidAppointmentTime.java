package sadek.doctorAppointments.appointmentsBooking.internal.domain.appointment.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidAppointmentTime extends DomainException {
  public InvalidAppointmentTime() {
    super("", Error.NONE);
  }

  public InvalidAppointmentTime(String message) {
    super(message, Error.NONE);
  }

  public InvalidAppointmentTime(String message, Error error) {
    super(message, error);
  }

  public InvalidAppointmentTime(Error error) {
    super("", error);
  }
}
