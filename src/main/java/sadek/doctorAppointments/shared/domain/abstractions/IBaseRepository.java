package sadek.doctorAppointments.shared.domain.abstractions;

import sadek.doctorAppointments.shared.domain.Entity;

public interface IBaseRepository<T extends Entity<?>> {
    void save(T model);
}
