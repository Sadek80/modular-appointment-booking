package sadek.doctorAppointments.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Error {
    private final String code;
    private final String description;
    private final ErrorType type;

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
