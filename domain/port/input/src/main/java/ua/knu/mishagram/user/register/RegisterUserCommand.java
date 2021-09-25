package ua.knu.mishagram.user.register;

public class RegisterUserCommand {
    private final String email;
    private final String password;
    private final String passwordConfirmation;

    public RegisterUserCommand(
        String email,
        String password,
        String passwordConfirmation
    ) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
