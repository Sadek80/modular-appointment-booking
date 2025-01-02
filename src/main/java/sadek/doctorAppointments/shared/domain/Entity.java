package sadek.doctorAppointments.shared.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Entity<TID> {
    @Getter
    @Setter
    private TID id;

    private final List<IDomainEvent> domainEvents = new ArrayList<>();

    public List<IDomainEvent> occurredEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void raiseDomainEvent(IDomainEvent event) {
        domainEvents.add(event);
    }
}
