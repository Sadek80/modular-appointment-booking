package sadek.doctorAppointments.shared.application.exceptions;

import sadek.doctorAppointments.shared.domain.Error;

public abstract class AppException extends RuntimeException {

    protected AppException(String message) {
        super(message);
    }

    protected AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class RequirementException extends AppException {
        public RequirementException(String description) {
            super(description);
        }
    }

    public static class BadRequestException extends AppException {
        public BadRequestException(String description) {
            super("Bad Request: " + (description != null ? description : ""));
        }
    }

    public static class UnAuthorized extends AppException {
        public UnAuthorized(String description) {
            super("Unauthorized: " + (description != null ? description : ""));
        }

        public UnAuthorized() {
            super("Unauthorized");
        }

    }

    public static class NotFoundException extends AppException {
        public NotFoundException(String description) {
            super("Not Found: " + (description != null ? description : ""));
        }
    }

    public static class IntegrationException extends AppException {
        public IntegrationException(Error error) {
            super("Error Happened: " + error.description());
        }
    }
}
