package ua.knu.mishagram.errorhandling;

import javax.validation.constraints.NotNull;

public class ErrorDto {

    @NotNull
    public ErrorReason errorReason;
    @NotNull
    public String message;

    public ErrorDto(@NotNull ErrorReason errorReason, @NotNull String message) {
        this.errorReason = errorReason;
        this.message = message;
    }

    @NotNull
    public ErrorReason getErrorReason() {
        return errorReason;
    }

    @NotNull
    public String getMessage() {
        return message;
    }
}
