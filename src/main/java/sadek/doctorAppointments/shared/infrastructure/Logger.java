package sadek.doctorAppointments.shared.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.domain.ILogger;

@Component
@Slf4j
@RequiredArgsConstructor
public class Logger implements ILogger {

    @Override
    public void trace(String var1) {
        log.trace(var1);
    }

    @Override
    public void trace(String var1, Object... var2) {
        log.trace(var1, var2);
    }

    @Override
    public void debug(String var1) {
        log.debug(var1);
    }

    @Override
    public void debug(String var1, Object... var2) {
        log.debug(var1, var2);
    }

    @Override
    public void info(String var1) {
        log.info(var1);
    }

    @Override
    public void info(String var1, Object... var2) {
        log.info(var1, var2);
    }

    @Override
    public void warn(String var1) {
        log.warn(var1);
    }

    @Override
    public void warn(String var1, Object... var2) {
        log.warn(var1, var2);
    }

    @Override
    public void error(String var1) {
        log.error(var1);
    }

    @Override
    public void error(String var1, Object... var2) {
        log.error(var1, var2);
    }

    @Override
    public void error(String var1, Throwable var2) {
        log.error(var1, var2);
    }
}
