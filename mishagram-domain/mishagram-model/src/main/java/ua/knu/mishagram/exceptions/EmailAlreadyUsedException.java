package ua.knu.mishagram.exceptions;

import org.jetbrains.annotations.NotNull;

public class EmailAlreadyUsedException extends RuntimeException {

    @NotNull
    private final String email;

    public EmailAlreadyUsedException(@NotNull String email, @NotNull String message) {
        super(message);
        this.email = email;
    }

    @NotNull
    public String getEmail() {
        return email;
    }
}
