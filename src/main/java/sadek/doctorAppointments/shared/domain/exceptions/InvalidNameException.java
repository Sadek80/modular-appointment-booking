package sadek.doctorAppointments.shared.domain.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class InvalidNameException extends DomainException {
    public InvalidNameException() {
        super("", sadek.doctorAppointments.shared.domain.Error.NONE);
    }

    public InvalidNameException(String message) {
        super(message, sadek.doctorAppointments.shared.domain.Error.NONE);
    }

    public InvalidNameException(String message, sadek.doctorAppointments.shared.domain.Error error) {
        super(message, error);
    }

    public InvalidNameException(Error error) {
        super("", error);
    }}
