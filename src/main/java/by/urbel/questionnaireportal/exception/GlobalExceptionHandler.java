package by.urbel.questionnaireportal.exception;

import by.urbel.questionnaireportal.service.exceptions.AccessDeniedException;
import by.urbel.questionnaireportal.service.exceptions.ChangePasswordException;
import by.urbel.questionnaireportal.service.exceptions.EmailAlreadyUsedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleSaveUserException(RuntimeException e) {
        return new Response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleAuthenticationExceptions() {
        return new Response(HttpStatus.BAD_REQUEST, "Incorrect email or password.");
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleEntityNotFoundException(EntityNotFoundException e) {
        return new Response(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response handleEntityExistsException(EntityExistsException e) {
        return new Response(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidationExceptions(MethodArgumentNotValidException e) {
        Response response = new Response(HttpStatus.BAD_REQUEST, "Invalid fields of dto entity.");
        Map<String, List<String>> errors = e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(error ->
                                ((FieldError) error).getField(),
                        Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));
        response.setValidationErrors(errors);
        return response;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new Response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response handleAccessDeniedException(AccessDeniedException e) {
        return new Response(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleChangePasswordException(ChangePasswordException e) {
        return new Response(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
