package ua.knu.mishagram;

import ua.knu.mishagram.errorhandling.ErrorReason;

public class ValidationException extends RuntimeException {

    private final ErrorReason errorReason;

    public ValidationException(String message, ErrorReason errorReason) {
        super(message);
        this.errorReason = errorReason;
    }

    public ErrorReason getErrorReason() {
        return errorReason;
    }

}
