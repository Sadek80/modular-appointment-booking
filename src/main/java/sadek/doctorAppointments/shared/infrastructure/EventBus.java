package sadek.doctorAppointments.shared.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;
import sadek.doctorAppointments.shared.application.IEventBus;
import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

@Component
@RequiredArgsConstructor
public class EventBus implements IEventBus {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(IDomainEvent domainEvent) {
        eventPublisher.publishEvent(domainEvent);
    }

    @Override
    public void publishIntegrationEvent(IIntegrationEvent integrationEvent) {
        eventPublisher.publishEvent(integrationEvent);
    }
}
