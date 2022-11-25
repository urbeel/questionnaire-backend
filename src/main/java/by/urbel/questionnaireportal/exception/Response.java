package by.urbel.questionnaireportal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class Response {
    private final HttpStatus status;
    private final int code;
    private final String message;

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.code = status.value();
        this.message = message;
    }
}
