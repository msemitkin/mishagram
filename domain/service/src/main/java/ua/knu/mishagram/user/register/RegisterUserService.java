package ua.knu.mishagram.user.register;

import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.SaveUserPort;

import java.util.Optional;

public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    public RegisterUserService(
        LoadUserPort loadUserPort,
        SaveUserPort saveUserPort
    ) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
    }

    @Override
    public void registerUser(RegisterUserCommand registerUserCommand) {
        User user = mapToUser(registerUserCommand);
        String requestedEmail = user.getEmail();
        Optional<User> existedUser = loadUserPort.loadByEmail(requestedEmail);
        if (existedUser.isPresent()) {
            throw new EmailAlreadyUsedException(requestedEmail, "Email already used");
        }
        saveUserPort.saveUser(user);
    }

    private User mapToUser(RegisterUserCommand registerUserCommand) {
        return new User(0, registerUserCommand.getEmail(), false);
    }
}
