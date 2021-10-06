package ua.knu.mishagram.user.register;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class RegisterUserRequest {
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    private String password;
    @NotNull(message = "Password confirmation is required")
    @NotBlank(message = "Password confirmation is required")
    private String passwordConfirmation;

    RegisterUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
