package sadek.doctorAppointments.shared.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDateTimeProvider {
    LocalDate nowDate();
    LocalDateTime nowDateTime();
}
