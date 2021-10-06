package ua.knu.mishagram.user.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.User;
import ua.knu.mishagram.test.util.TestUtils;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

    private static final int USER_ID = 777;
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(1010, 10, 10, 10, 10);

    @Mock
    private LoadUserPort loadUserPort;

    @InjectMocks
    private GetUserService getUserService;

    @Test
    void getById_shouldThrowException_whenUserNotExists() {
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> getUserService.getById(USER_ID));
    }

    @Test
    void getById_shouldThrowException_whenUserIsDeleted() {
        User user = new User(USER_ID, "email", true, TEST_DATE, "pass");
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserNotFoundException.class, () -> getUserService.getById(USER_ID));
    }

    @Test
    void getById_successFlow() {
        User user = new User(USER_ID, "email", false, TEST_DATE, "pass");
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.of(user));

        User actualUser = getUserService.getById(USER_ID);

        TestUtils.assertJsonModelsEquals(user, actualUser);
    }
}