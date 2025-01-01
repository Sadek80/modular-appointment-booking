package sadek.doctorAppointments.shared.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Entity<TID> {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public List<DomainEvent> occurredEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void raiseDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }
}
