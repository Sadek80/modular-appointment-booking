package sadek.doctorAppointments.shared.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sadek.doctorAppointments.shared.domain.Error;
import sadek.doctorAppointments.shared.domain.Result;

import java.util.function.Function;

public abstract class BaseController {
    protected <T> ResponseEntity<?> handleResult(Result<T> result, HttpStatus successStatus) {
        return result.match(
                onSuccess(successStatus),
                onFailure()
        );
    }

    protected <T> ResponseEntity<?> handleResult(Result<T> result) {
        return handleResult(result, HttpStatus.OK);
    }

    private static <T> Function<T, ResponseEntity<?>> onSuccess(HttpStatus successStatus) {
        return value -> {
            if (successStatus == HttpStatus.NO_CONTENT) {
                return ResponseEntity.status(successStatus).build();
            } else {
                return ResponseEntity.status(successStatus).body(value);
            }
        };
    }

    private static <T> Function<Result<T>, ResponseEntity<?>> onFailure() {
        return errorResult -> {
            Error error = errorResult.getError();
            HttpStatus status = getHttpStatus(error);

            return ResponseEntity.status(status).body(error);
        };
    }

    private static HttpStatus getHttpStatus(Error error) {
        return switch (error.getType()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
