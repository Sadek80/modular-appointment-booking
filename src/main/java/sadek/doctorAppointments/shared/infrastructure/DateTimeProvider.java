package sadek.doctorAppointments.shared.infrastructure;

import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.domain.abstractions.IDateTimeProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DateTimeProvider implements IDateTimeProvider {
    @Override
    public LocalDate nowDate() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }
}
