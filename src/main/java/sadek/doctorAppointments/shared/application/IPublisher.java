package sadek.doctorAppointments.shared.application;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

public interface IPublisher {
    void publish(IDomainEvent domainEvent);
}
