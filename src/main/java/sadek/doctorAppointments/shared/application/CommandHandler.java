package sadek.doctorAppointments.shared.application;

public interface CommandHandler<Command, TResult> {
    TResult handle(Command command);
}