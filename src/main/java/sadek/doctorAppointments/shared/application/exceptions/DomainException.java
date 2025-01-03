package sadek.doctorAppointments.shared.application.exceptions;

import lombok.Getter;
import sadek.doctorAppointments.shared.domain.Error;

public abstract class DomainException extends RuntimeException {
    @Getter
    private final Error error;

    protected DomainException(String message, Error error) {
        super(message);
        this.error = error;
    }
}