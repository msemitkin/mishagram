package ua.knu.mishagram.user.register;

import org.jetbrains.annotations.NotNull;

public class EmailAlreadyUsedException extends RuntimeException {

    @NotNull
    private final String email;

    EmailAlreadyUsedException(@NotNull String email, @NotNull String message) {
        super(message);
        this.email = email;
    }

    @NotNull
    public String getEmail() {
        return email;
    }
}