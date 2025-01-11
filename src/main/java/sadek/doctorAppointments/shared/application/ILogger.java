package sadek.doctorAppointments.shared.application;

public interface ILogger {
    void trace(String var1);
    void trace(String var1, Object... var2);

    void debug(String var1);
    void debug(String var1, Object... var2);

    void info(String var1);
    void info(String var1, Object... var2);

    void warn(String var1);
    void warn(String var1, Object... var2);

    void error(String var1);
    void error(String var1, Object... var2);
    void error(String var1, Throwable var2);
}
