package ua.knu.mishagram.post.get;

import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.GetUserPostsAfterDateTimePort;
import ua.knu.mishagram.subscription.GetUserSubscriptionsPort;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class GetLatestPostsFromUserSubscriptionsService implements GetLatestPostsFromSubscriptionsUseCase {

    private final GetUserSubscriptionsPort getUserSubscriptionsPort;
    private final GetUserPostsAfterDateTimePort getUserPostsAfterDateTimePort;

    public GetLatestPostsFromUserSubscriptionsService(
        GetUserSubscriptionsPort getUserSubscriptionsPort,
        GetUserPostsAfterDateTimePort getUserPostsAfterDateTimePort
    ) {
        this.getUserSubscriptionsPort = getUserSubscriptionsPort;
        this.getUserPostsAfterDateTimePort = getUserPostsAfterDateTimePort;
    }

    @Override
    public List<Post> getAllFromUserSubscriptionsInPeriod(int userId, Period period) {
        LocalDateTime loadFromDateTime = LocalDateTime.now().minus(period);
        List<Integer> subscriptions = getUserSubscriptionsPort.getAllByUserId(userId);
        return getUserPostsAfterDateTimePort.getAll(subscriptions, loadFromDateTime);
    }
}
