package ua.knu.mishagram.post.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.subscription.LoadUserSubscriptionsPort;
import ua.knu.mishagram.test.util.TestUtils;
import ua.knu.mishagram.time.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLatestPostsFromUserSubscriptionsServiceTest {

    private static final int USER_ID = 777;
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(1010, 10, 10, 10, 10);
    private static final Period PERIOD = Period.ofDays(1);
    @Mock
    private LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    @Mock
    private LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort;
    @Mock
    private DateTimeProvider dateTimeProvider;
    @Mock
    private PostCompositeBuilderService postCompositeBuilderService;

    @InjectMocks
    private GetLatestPostsFromUserSubscriptionsService getLatestPostsFromUserSubscriptionsService;

    @Test
    void getAllFromUserSubscriptionsInPeriod_shouldReturnEmptyList_whenUserNotSubscribedToAnyUser() {
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID))
            .thenReturn(Collections.emptyList());

        List<PostComposite> posts = getLatestPostsFromUserSubscriptionsService
            .getAllFromUserSubscriptionsInPeriod(USER_ID, PERIOD);

        Assertions.assertTrue(posts.isEmpty());
    }

    @Test
    void getAllFromUserSubscriptionsInPeriod_shouldReturnEmptyList_whenNoPostsWereFound() {
        List<Integer> subscriptions = List.of(33, 44, 55);
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID)).thenReturn(subscriptions);
        when(dateTimeProvider.now()).thenReturn(TEST_DATE);
        when(loadUserPostsAfterDateTimePort.getAllAfterDateTime(subscriptions, TEST_DATE.minus(PERIOD)))
            .thenReturn(Collections.emptyList());

        List<PostComposite> posts = getLatestPostsFromUserSubscriptionsService
            .getAllFromUserSubscriptionsInPeriod(USER_ID, PERIOD);

        Assertions.assertTrue(posts.isEmpty());
    }

    @Test
    void getAllFromUserSubscriptionsInPeriod_successFlow() {
        List<Integer> subscriptions = List.of(33, 44, 55);
        List<Post> posts = List.of(
            new Post(1, 33, 333,"text33", TEST_DATE, false),
            new Post(2, 44, 444,"text44", TEST_DATE, false)
        );
        when(loadUserSubscriptionsPort.getAllByUserId(USER_ID)).thenReturn(subscriptions);
        when(dateTimeProvider.now()).thenReturn(TEST_DATE);
        when(loadUserPostsAfterDateTimePort.getAllAfterDateTime(subscriptions, TEST_DATE.minus(PERIOD)))
            .thenReturn(posts);
        List<PostComposite> postComposites = List.of(
            new PostComposite(1, 33, new Content(333, "name333", "ext333", new byte[3]), "text33", TEST_DATE, false),
            new PostComposite(1, 44, new Content(444, "name444", "ext444", new byte[4]), "text33", TEST_DATE, false)
        );
        when(postCompositeBuilderService.getPostComposites(posts)).thenReturn(postComposites);
        List<PostComposite> actualPosts = getLatestPostsFromUserSubscriptionsService
            .getAllFromUserSubscriptionsInPeriod(USER_ID, PERIOD);

        TestUtils.assertJsonModelsEquals(postComposites, actualPosts);
    }
}