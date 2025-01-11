package sadek.doctorAppointments.shared.application;

public interface IUserContext<T, TID> {
    T getUser();
    TID getUserId();
}
