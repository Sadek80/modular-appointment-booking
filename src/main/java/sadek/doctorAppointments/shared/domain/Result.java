package sadek.doctorAppointments.shared.domain;

import java.util.function.Function;
import java.util.function.Supplier;

public class Result<T> {
    private final T value;
    private final Error error;
    private final boolean isSuccess;

    private Result(T value, Error error, boolean isSuccess) {
        this.value = value;
        this.error = error;
        this.isSuccess = isSuccess;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, Error.NONE, true);
    }

    public static <T> Result<T> failure(Error error) {
        return new Result<>(null, error, false);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public T getValue() {
        if (!isSuccess) {
            throw new IllegalStateException("Cannot get value from a failed result.");
        }
        return value;
    }

    public Error getError() {
        if (isSuccess) {
            throw new IllegalStateException("Cannot get error from a successful result.");
        }
        return error;
    }

    public static <TOut> TOut match(Result<Void> result, Supplier<TOut> onSuccess, Function<Result<Void>, TOut> onFailure) {
        return result.isSuccess ? onSuccess.get() : onFailure.apply(result);
    }

    public <TOut> TOut match(Function<T, TOut> onSuccess, Function<Result<T>, TOut> onFailure) {
        return isSuccess ? onSuccess.apply(value) : onFailure.apply(this);
    }

    @Override
    public String toString() {
        return isSuccess
                ? "Success(value=" + value + ")"
                : "Failure(error=" + error + ")";
    }
}