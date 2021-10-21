package ua.knu.mishagram.security.accesscheckers;

import org.springframework.security.access.AccessDeniedException;

public class DataAccessDeniedException extends AccessDeniedException {

    DataAccessDeniedException(String message) {
        super(message);
    }

}
