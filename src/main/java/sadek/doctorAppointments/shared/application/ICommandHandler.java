package sadek.doctorAppointments.shared.application;

public interface ICommandHandler<Command, TResult> {
    TResult handle(Command command);
}