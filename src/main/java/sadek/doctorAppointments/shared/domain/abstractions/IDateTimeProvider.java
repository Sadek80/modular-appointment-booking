package sadek.doctorAppointments.shared.domain.abstractions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDateTimeProvider {
    LocalDate nowDate();
    LocalDateTime nowDateTime();
}
