package sadek.doctorAppointments.doctorAppointmentManagement.internal.core.exceptions;

import sadek.doctorAppointments.shared.application.exceptions.DomainException;
import sadek.doctorAppointments.shared.domain.Error;

public class AppointmentUpdateStageViolation extends DomainException {
    public AppointmentUpdateStageViolation() {
        super("", Error.NONE);
    }

    public AppointmentUpdateStageViolation(String message) {
        super(message, Error.NONE);
    }

    public AppointmentUpdateStageViolation(String message, Error error) {
        super(message, error);
    }

    public AppointmentUpdateStageViolation(Error error) {
        super("", error);
    }}
