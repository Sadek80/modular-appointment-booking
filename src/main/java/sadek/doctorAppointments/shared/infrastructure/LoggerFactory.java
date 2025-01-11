package sadek.doctorAppointments.shared.infrastructure;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.application.ILogger;
import sadek.doctorAppointments.shared.application.ILoggerFactory;

@Component
public class LoggerFactory implements ILoggerFactory {

    @Override
    public ILogger getLogger(Class<?> objectClass) {
        Logger logger = org.slf4j.LoggerFactory.getLogger(objectClass);
        return new sadek.doctorAppointments.shared.infrastructure.Logger(logger);
    }
}
