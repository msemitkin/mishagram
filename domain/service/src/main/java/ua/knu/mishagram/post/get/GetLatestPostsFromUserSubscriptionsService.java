package ua.knu.mishagram.post.get;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.subscription.LoadUserSubscriptionsPort;
import ua.knu.mishagram.time.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

@Service
public class GetLatestPostsFromUserSubscriptionsService implements GetLatestPostsFromSubscriptionsUseCase {

    private final LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    private final LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort;
    private final DateTimeProvider dateTimeProvider;
    private final PostCompositeBuilderService postCompositeBuilderService;

    public GetLatestPostsFromUserSubscriptionsService(
        LoadUserSubscriptionsPort loadUserSubscriptionsPort,
        LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort,
        DateTimeProvider dateTimeProvider,
        PostCompositeBuilderService postCompositeBuilderService
    ) {
        this.loadUserSubscriptionsPort = loadUserSubscriptionsPort;
        this.loadUserPostsAfterDateTimePort = loadUserPostsAfterDateTimePort;
        this.dateTimeProvider = dateTimeProvider;
        this.postCompositeBuilderService = postCompositeBuilderService;
    }

    @Override
    public List<PostComposite> getAllFromUserSubscriptionsInPeriod(int userId, Period period) {
        List<Integer> subscriptions = loadUserSubscriptionsPort.getAllByUserId(userId);
        if (subscriptions.isEmpty()) {
            return Collections.emptyList();
        }
        LocalDateTime loadFromDateTime = dateTimeProvider.now().minus(period);
        List<Post> posts = loadUserPostsAfterDateTimePort.getAllAfterDateTime(subscriptions, loadFromDateTime);
        return postCompositeBuilderService.getPostComposites(posts);
    }
}
