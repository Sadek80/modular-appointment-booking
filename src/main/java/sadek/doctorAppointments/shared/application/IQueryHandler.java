package sadek.doctorAppointments.shared.application;

public interface IQueryHandler<IQuery, TResult> {
    TResult handle(IQuery query);
}
