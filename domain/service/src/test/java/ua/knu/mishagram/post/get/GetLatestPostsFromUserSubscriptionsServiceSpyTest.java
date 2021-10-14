package ua.knu.mishagram.post.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.content.LoadFileContentPort;
import ua.knu.mishagram.subscription.LoadUserSubscriptionsPort;
import ua.knu.mishagram.time.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetLatestPostsFromUserSubscriptionsServiceSpyTest {

    private static final int USER_ID = 777;
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(1010, 10, 10, 10, 10);
    private static final Period PERIOD = Period.ofDays(1);

    private LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    private LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort;
    private DateTimeProvider dateTimeProvider;
    private PostCompositeBuilderService postCompositeBuilderService;

    private GetLatestPostsFromUserSubscriptionsService getLatestPostsFromUserSubscriptionsService;

    @BeforeEach
    void setUp() {
        loadUserSubscriptionsPort = mock(LoadUserSubscriptionsPort.class);
        loadUserPostsAfterDateTimePort = mock(LoadUserPostsAfterDateTimePort.class);
        dateTimeProvider = mock(DateTimeProvider.class);
        LoadFileContentPort loadFileContentPort = mock(LoadFileContentPort.class);
        postCompositeBuilderService = spy(new PostCompositeBuilderService(loadFileContentPort));

        MockitoAnnotations.openMocks(this);

        getLatestPostsFromUserSubscriptionsService = new GetLatestPostsFromUserSubscriptionsService(
            loadUserSubscriptionsPort,
            loadUserPostsAfterDateTimePort,
            dateTimeProvider,
            postCompositeBuilderService
        );
    }

    @Test
    public void getAllFromUserSubscriptionsInPeriod_shouldReturnEmptyList_whenThereAreNoNewPostsFromSubscriptions() {
        List<Integer> subscriptions = List.of(1, 2, 3, 4);
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID)).thenReturn(subscriptions);
        when(dateTimeProvider.now()).thenReturn(TEST_DATE_TIME);
        when(loadUserPostsAfterDateTimePort.getAllAfterDateTime(subscriptions, TEST_DATE_TIME.minus(PERIOD)))
            .thenReturn(Collections.emptyList());

        List<PostComposite> actualPosts = getLatestPostsFromUserSubscriptionsService.getAllFromUserSubscriptionsInPeriod(USER_ID, PERIOD);

        Assertions.assertTrue(actualPosts.isEmpty());
        verify(postCompositeBuilderService).getPostComposites(Collections.emptyList());
    }
}
