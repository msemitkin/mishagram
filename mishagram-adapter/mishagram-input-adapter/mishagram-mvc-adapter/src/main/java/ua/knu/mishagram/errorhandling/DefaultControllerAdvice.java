package ua.knu.mishagram.errorhandling;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.knu.mishagram.exceptions.ContentNotFoundException;
import ua.knu.mishagram.exceptions.EmailAlreadyUsedException;
import ua.knu.mishagram.exceptions.InvalidSubscriptionException;
import ua.knu.mishagram.exceptions.UserNotFoundException;

@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(
        UserNotFoundException e,
        Model model
    ) {
        model.addAttribute("message", "Requested user does not exist");
        return "error";
    }

    @ExceptionHandler(InvalidSubscriptionException.class)
    public String handleInvalidSubscriptionException(
        InvalidSubscriptionException e,
        Model model
    ) {
        model.addAttribute("message", "Failed to subscribe to user");
        return "error";
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public String handleEmailAlreadyUsedException(
        EmailAlreadyUsedException e,
        Model model
    ) {
        model.addAttribute("message", "User with such email already exists");
        return "error";
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public String handleContentNotFoundException(
        ContentNotFoundException e,
        Model model
    ) {
        model.addAttribute("message", "Failed to load requested content");
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "error";
    }
}
