package ua.knu.mishagram.user.register;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.knu.mishagram.User;
import ua.knu.mishagram.exceptions.EmailAlreadyUsedException;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.SaveUserPort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(1010, 10, 10, 10, 10);

    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private SaveUserPort saveUserPort;
    @Mock
    private DateTimeProvider dateTimeProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserService registerUserService;

    @Test
    void registerUser_shouldThrowException_whenEmailIsAlreadyUsed() {
        when(loadUserPort.loadByEmail("some email"))
            .thenReturn(Optional.of(new User(55, "some email", false, TEST_DATE, "pass")));

        RegisterUserCommand registerUserCommand = new RegisterUserCommand("some email", "pass");
        Assertions.assertThrows(EmailAlreadyUsedException.class, () -> registerUserService.registerUser(registerUserCommand));
    }

    @Test
    void registerUser_successFlow() {
        when(loadUserPort.loadByEmail("some email")).thenReturn(Optional.empty());
        when(dateTimeProvider.now()).thenReturn(TEST_DATE);
        when(passwordEncoder.encode("pass")).thenReturn("encoded pass");

        RegisterUserCommand registerUserCommand = new RegisterUserCommand("some email", "pass");
        registerUserService.registerUser(registerUserCommand);

        User expectedUser = new User(0, "some email", false, TEST_DATE, "encoded pass");
        verify(saveUserPort).saveUser(refEq(expectedUser));
    }
}