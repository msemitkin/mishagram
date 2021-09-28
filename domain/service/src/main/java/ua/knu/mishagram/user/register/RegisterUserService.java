package ua.knu.mishagram.user.register;

import ua.knu.mishagram.User;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.SaveUserPort;

import java.util.Optional;

public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final DateTimeProvider dateTimeProvider;

    public RegisterUserService(
        LoadUserPort loadUserPort,
        SaveUserPort saveUserPort,
         DateTimeProvider dateTimeProvider
    ) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void registerUser(RegisterUserCommand registerUserCommand) {
        String requestedEmail = registerUserCommand.getEmail();
        Optional<User> existedUser = loadUserPort.loadByEmail(requestedEmail);
        if (existedUser.isPresent()) {
            throw new EmailAlreadyUsedException(requestedEmail, "Email already used");
        }
        User user = mapToUser(registerUserCommand);
        saveUserPort.saveUser(user);
    }

    private User mapToUser(RegisterUserCommand registerUserCommand) {
        return new User(0, registerUserCommand.getEmail(), false, dateTimeProvider.now());
    }
}
