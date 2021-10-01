package ua.knu.mishagram.user.delete;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UpdateUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.knu.mishagram.test.util.TestUtils.is;

@ExtendWith(MockitoExtension.class)
class DeleteUserServiceTest {

    private static final int USER_ID = 777;
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(10, 10, 10, 10, 10);

    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private UpdateUserPort updateUserPort;

    @InjectMocks
    private DeleteUserService deleteUserService;

    @Test
    void deleteUser_shouldThrowException_whenUserNotExists() {
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> deleteUserService.deleteUser(USER_ID));
    }

    @Test
    void deleteUser_shouldUpdateUser_whenUserExists() {
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.of(new User(USER_ID, "email", false, TEST_DATE, "pass")));

        deleteUserService.deleteUser(USER_ID);

        verify(updateUserPort).update(is(new User(USER_ID, "email", true, TEST_DATE, "pass")));
    }

    @Test
    void deleteUser_shouldDoNothing_whenUserDeleted() {
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.of(new User(USER_ID, "email", true, TEST_DATE, "pass")));

        deleteUserService.deleteUser(USER_ID);

        verify(updateUserPort, never()).update(any());
    }
}