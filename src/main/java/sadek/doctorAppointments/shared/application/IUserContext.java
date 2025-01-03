package sadek.doctorAppointments.shared.application;

import lombok.Getter;

public interface IUserContext<T, TID> {
    T getUser();
    TID getUserId();
}
