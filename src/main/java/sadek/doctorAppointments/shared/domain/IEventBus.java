package sadek.doctorAppointments.shared.domain;

public interface IEventBus {
    void publish(IDomainEvent domainEvent);
    void publishIntegrationEvent(IIntegrationEvent integrationEvent);
}
