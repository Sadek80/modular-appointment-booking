package sadek.doctorAppointments.shared.application;

import sadek.doctorAppointments.shared.domain.abstractions.IIntegrationEvent;

public interface IEventBus {
    void publish(IIntegrationEvent integrationEvent);
}
