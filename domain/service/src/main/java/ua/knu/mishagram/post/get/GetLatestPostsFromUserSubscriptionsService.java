package ua.knu.mishagram.post.get;

import ua.knu.mishagram.Post;
import ua.knu.mishagram.subscription.LoadUserSubscriptionsPort;
import ua.knu.mishagram.time.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

public class GetLatestPostsFromUserSubscriptionsService implements GetLatestPostsFromSubscriptionsUseCase {

    private final LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    private final LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort;
    private final DateTimeProvider dateTimeProvider;

    public GetLatestPostsFromUserSubscriptionsService(
        LoadUserSubscriptionsPort loadUserSubscriptionsPort,
        LoadUserPostsAfterDateTimePort loadUserPostsAfterDateTimePort,
        DateTimeProvider dateTimeProvider
    ) {
        this.loadUserSubscriptionsPort = loadUserSubscriptionsPort;
        this.loadUserPostsAfterDateTimePort = loadUserPostsAfterDateTimePort;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public List<Post> getAllFromUserSubscriptionsInPeriod(int userId, Period period) {
        List<Integer> subscriptions = loadUserSubscriptionsPort.getAllByUserId(userId);
        if (subscriptions.isEmpty()) {
            return Collections.emptyList();
        }
        LocalDateTime loadFromDateTime = dateTimeProvider.now().minus(period);
        return loadUserPostsAfterDateTimePort.getAllAfterDateTime(subscriptions, loadFromDateTime);
    }
}
