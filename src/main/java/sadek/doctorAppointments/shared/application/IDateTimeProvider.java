package sadek.doctorAppointments.shared.application;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDateTimeProvider {
    LocalDate nowDate();
    LocalDateTime nowDateTime();
}
