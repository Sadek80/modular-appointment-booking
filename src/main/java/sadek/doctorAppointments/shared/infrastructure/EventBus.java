package sadek.doctorAppointments.shared.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.domain.IDomainEvent;
import sadek.doctorAppointments.shared.domain.IEventBus;

@Component
@RequiredArgsConstructor
public class EventBus implements IEventBus {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(IDomainEvent domainEvent) {
        eventPublisher.publishEvent(domainEvent);
    }
}
