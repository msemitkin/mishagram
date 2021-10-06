package ua.knu.mishagram.user.register;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterUserCommand {
    @NotNull
    private final String email;
    @NotNull
    private final String password;

    public RegisterUserCommand(
        @NotNull String email,
        @NotNull String password
    ) {
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

}
