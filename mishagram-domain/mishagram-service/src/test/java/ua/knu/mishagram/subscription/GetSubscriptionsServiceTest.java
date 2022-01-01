package ua.knu.mishagram.subscription;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GetSubscriptionsServiceTest {

    private static final int USER_ID = 666;
    @Mock
    private LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    @Mock
    private LoadUserPort loadUserPort;

    @InjectMocks
    private GetSubscriptionsService getSubscriptionsService;

    @Test
    void getAllByUserId_returnsEmptyList_whenWhenUserIsNotSubscribedToAnybody() {
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID)).thenReturn(Collections.emptyList());

        List<User> subscriptions = getSubscriptionsService.getAllByUserId(USER_ID);

        Assertions.assertTrue(subscriptions.isEmpty());
    }

    @Test
    void getAllByUserId_successFlow() {
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID)).thenReturn(List.of(1, 2));
        List<User> users = List.of(
            new User(1, "email1", false, LocalDateTime.now(), "hash1"),
            new User(2, "email2", false, LocalDateTime.now(), "hash2")
        );
        when(loadUserPort.loadAll(List.of(1, 2))).thenReturn(users);

        List<User> actualUsers = getSubscriptionsService.getAllByUserId(USER_ID);

        assertThat(actualUsers)
            .usingRecursiveComparison()
            .isEqualTo(users);
    }
}