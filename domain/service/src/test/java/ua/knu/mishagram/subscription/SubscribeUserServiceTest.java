package ua.knu.mishagram.subscription;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Subscription;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscribeUserServiceTest {

    @Mock
    private SubscribeUserPost subscribeUserPost;
    @Mock
    private LoadUserSubscriptionsPort loadUserSubscriptionsPort;

    @InjectMocks
    private SubscribeUserService subscribeUserService;

    @Test
    void subscribe_shouldThrowException_whenUserIdsMatch() {
        Assertions.assertThrows(
            InvalidSubscriptionException.class,
            () -> subscribeUserService.subscribe(new Subscription(100, 100))
        );
    }

    @Test
    void subscribe_successFlow() {
        when(loadUserSubscriptionsPort.getAllByUserId(100)).thenReturn(List.of(300, 400, 500));

        subscribeUserService.subscribe(new Subscription(100, 200));

        verify(subscribeUserPost).subscribe(100, 200);
    }
}