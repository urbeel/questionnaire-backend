package by.urbel.questionnaireportal.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Response {
    private final HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> validationErrors;

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.code = status.value();
        this.message = message;
    }
}
