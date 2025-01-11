package sadek.doctorAppointments.shared.infrastructure;

import sadek.doctorAppointments.shared.application.ILogger;

public class Logger implements ILogger {
    private final org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void trace(String var1) {
        logger.trace(var1);
    }

    @Override
    public void trace(String var1, Object... var2) {
        logger.trace(var1, var2);
    }

    @Override
    public void debug(String var1) {
        logger.debug(var1);
    }

    @Override
    public void debug(String var1, Object... var2) {
        logger.debug(var1, var2);
    }

    @Override
    public void info(String var1) {
        logger.info(var1);
    }

    @Override
    public void info(String var1, Object... var2) {
        logger.info(var1, var2);
    }

    @Override
    public void warn(String var1) {
        logger.warn(var1);
    }

    @Override
    public void warn(String var1, Object... var2) {
        logger.warn(var1, var2);
    }

    @Override
    public void error(String var1) {
        logger.error(var1);
    }

    @Override
    public void error(String var1, Object... var2) {
        logger.error(var1, var2);
    }

    @Override
    public void error(String var1, Throwable var2) {
        logger.error(var1, var2);
    }
}
