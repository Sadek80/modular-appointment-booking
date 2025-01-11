package sadek.doctorAppointments.shared.application;

import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;
import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

public interface IEventBus {
    void publish(IDomainEvent domainEvent);
    void publishIntegrationEvent(IIntegrationEvent integrationEvent);
}
