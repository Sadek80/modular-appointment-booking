package sadek.doctorAppointments.shared.application;

public interface IQueryHandler<Query, TResult> {
    TResult handle(ICommand command);
}
