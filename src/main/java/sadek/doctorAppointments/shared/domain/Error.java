package sadek.doctorAppointments.shared.domain;

public record Error(String code, String description, ErrorType type) {
    public static final Error NONE = new Error("", "", ErrorType.FAILURE);
    public static final Error NULL_VALUE = new Error(
            "General.Null",
            "Null value was provided",
            ErrorType.FAILURE
    );

    public static Error failure(String code, String description) {
        return new Error(code, description, ErrorType.FAILURE);
    }

    public static Error notFound(String code, String description) {
        return new Error(code, description, ErrorType.NOT_FOUND);
    }

    public static Error unAuthorized(String code, String description) {
        return new Error(code, description, ErrorType.UNAUTHORIZED);
    }

    public static Error conflict(String code, String description) {
        return new Error(code, description, ErrorType.CONFLICT);
    }
}
