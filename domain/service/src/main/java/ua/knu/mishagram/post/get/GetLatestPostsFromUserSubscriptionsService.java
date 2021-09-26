package ua.knu.mishagram.post.get;

import ua.knu.mishagram.Post;
import ua.knu.mishagram.subscription.LoadUserSubscriptionsPort;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class GetLatestPostsFromUserSubscriptionsService implements GetLatestPostsFromSubscriptionsUseCase {

    private final LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    private final LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort;

    public GetLatestPostsFromUserSubscriptionsService(
        LoadUserSubscriptionsPort loadUserSubscriptionsPort,
        LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort
    ) {
        this.loadUserSubscriptionsPort = loadUserSubscriptionsPort;
        this.loadUserPostsAfterDateTimePort = loadUserPostsAfterDateTimePort;
    }

    @Override
    public List<Post> getAllFromUserSubscriptionsInPeriod(int userId, Period period) {
        LocalDateTime loadFromDateTime = LocalDateTime.now().minus(period);
        List<Integer> subscriptions = loadUserSubscriptionsPort.getAllByUserId(userId);
        return loadUserPostsAfterDateTimePort.getAll(subscriptions, loadFromDateTime);
    }
}
