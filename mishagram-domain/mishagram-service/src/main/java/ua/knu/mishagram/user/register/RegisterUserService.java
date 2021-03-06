package ua.knu.mishagram.user.register;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.exceptions.EmailAlreadyUsedException;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.SaveUserPort;

import java.util.Optional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final DateTimeProvider dateTimeProvider;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(
        LoadUserPort loadUserPort,
        SaveUserPort saveUserPort,
        DateTimeProvider dateTimeProvider,
        PasswordEncoder passwordEncoder
    ) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.dateTimeProvider = dateTimeProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterUserCommand registerUserCommand) {
        String requestedEmail = registerUserCommand.getEmail();
        Optional<User> existedUser = loadUserPort.loadByEmail(requestedEmail);
        if (existedUser.isPresent()) {
            throw new EmailAlreadyUsedException(requestedEmail, "Email already used");
        }
        String encodedPassword = passwordEncoder.encode(registerUserCommand.getPassword());
        User user = mapToUser(registerUserCommand, encodedPassword);
        saveUserPort.saveUser(user);
    }

    private User mapToUser(RegisterUserCommand registerUserCommand, String encodedPassword) {
        return new User(
            0,
            registerUserCommand.getEmail(),
            false,
            dateTimeProvider.now(),
            encodedPassword
        );
    }
}
