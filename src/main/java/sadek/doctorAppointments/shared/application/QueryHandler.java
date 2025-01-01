package sadek.doctorAppointments.shared.application;

public interface QueryHandler<Query, TResult> {
    TResult handle(Command command);
}
