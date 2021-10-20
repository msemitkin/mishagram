package ua.knu.mishagram.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.knu.mishagram.ValidationException;
import ua.knu.mishagram.exceptions.EmailAlreadyUsedException;
import ua.knu.mishagram.exceptions.UserNotFoundException;
import ua.knu.mishagram.post.UploadFileException;

import static ua.knu.mishagram.errorhandling.ErrorReason.EMAIL_ALREADY_USED_ERROR;
import static ua.knu.mishagram.errorhandling.ErrorReason.INTERNAL_ERROR;
import static ua.knu.mishagram.errorhandling.ErrorReason.UPLOAD_FILE_ERROR;
import static ua.knu.mishagram.errorhandling.ErrorReason.USER_NOT_FOUND_ERROR;

@RestControllerAdvice
public class WebApiControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(WebApiControllerAdvice.class);


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleUserNotFoundException(UserNotFoundException e) {
        logger.warn("Requested user was not found", e);
        return new ErrorDto(USER_NOT_FOUND_ERROR, "User not found");
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleEmailAlreadyUsedException(EmailAlreadyUsedException e) {
        return new ErrorDto(EMAIL_ALREADY_USED_ERROR, "User with given email already exists");
    }

    @ExceptionHandler(UploadFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleUploadFileException(UploadFileException e) {
        logger.info("Received invalid file", e);
        return new ErrorDto(UPLOAD_FILE_ERROR, "Failed to process file");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationException(ValidationException e) {
        return new ErrorDto(e.getErrorReason(), "Validation failed");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(Exception e) {
        logger.error("Caught unexpected exception", e);
        return new ErrorDto(INTERNAL_ERROR, "Something went wrong");
    }

}
