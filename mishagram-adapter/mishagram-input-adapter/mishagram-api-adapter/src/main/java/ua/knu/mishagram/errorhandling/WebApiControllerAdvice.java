package ua.knu.mishagram.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.knu.mishagram.exceptions.EmailAlreadyUsedException;
import ua.knu.mishagram.exceptions.UserNotFoundException;

import static ua.knu.mishagram.errorhandling.ErrorReason.EMAIL_ALREADY_USED;
import static ua.knu.mishagram.errorhandling.ErrorReason.USER_NOT_FOUND;

@RestControllerAdvice
public class WebApiControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(WebApiControllerAdvice.class);


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleUserNotFoundException(UserNotFoundException e) {
        logger.warn("Requested user was not found", e);
        return new ErrorDto(USER_NOT_FOUND, "User not found");
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        return new ErrorDto(EMAIL_ALREADY_USED, "User with given email already exists");
    }

}
