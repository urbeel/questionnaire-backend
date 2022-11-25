package by.urbel.questionnaireportal.exception;

import by.urbel.questionnaireportal.service.exceptions.EmailAlreadyUsedException;
import by.urbel.questionnaireportal.service.exceptions.PasswordConfirmationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailAlreadyUsedException.class, PasswordConfirmationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleRegistrationException(RuntimeException e) {
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
        return new Response(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler({EntityExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response handleEntityExistsException(EntityExistsException e) {
        return new Response(HttpStatus.CONFLICT,e.getMessage());
    }
}
