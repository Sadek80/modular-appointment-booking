package sadek.doctorAppointments.shared.domain.abstractions;

public interface IBaseRepository<T> {
    void save(T model);
}
