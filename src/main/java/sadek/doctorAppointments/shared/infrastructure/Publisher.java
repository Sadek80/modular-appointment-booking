package sadek.doctorAppointments.shared.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import sadek.doctorAppointments.shared.application.IPublisher;
import sadek.doctorAppointments.shared.domain.abstractions.IDomainEvent;

@Component
@RequiredArgsConstructor
public class Publisher implements IPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(IDomainEvent domainEvent) {
        eventPublisher.publishEvent(domainEvent);
    }
}
