package sadek.doctorAppointments.shared.domain.abstractions;

public interface ILoggerFactory {
    ILogger getLogger(Class<?> className);
}
