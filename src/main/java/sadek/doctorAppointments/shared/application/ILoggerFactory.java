package sadek.doctorAppointments.shared.application;

public interface ILoggerFactory {
    ILogger getLogger(Class<?> className);
}
