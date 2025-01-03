package sadek.doctorAppointments.shared.domain;

import lombok.Data;

@Data
public class Response<TData> {
    private TData data;
    private String message;

    private Response(TData data, String message){
        this.data = data;
        this.message = message;
    }

    public static <T> Response<T> create(T data) {
        return new Response<>(data, "");
    }

    public static <T> Response<T> create(T data, String message) {
        return new Response<>(data, message);
    }

    public static Response<Void> create() {
        return new Response<Void>(null, "");
    }

    public static Response<Void> create(String message) {
        return new Response<Void>(null, message);
    }
}
