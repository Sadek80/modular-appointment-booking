package sadek.doctorAppointments.shared.domain.abstractions;

public interface IEventBus {
    void publish(IDomainEvent domainEvent);
    void publishIntegrationEvent(IIntegrationEvent integrationEvent);
}
