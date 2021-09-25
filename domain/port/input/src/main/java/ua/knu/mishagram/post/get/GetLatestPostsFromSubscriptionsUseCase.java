package ua.knu.mishagram.post.get;

import ua.knu.mishagram.Post;

import java.time.Period;
import java.util.List;

public interface GetLatestPostsFromSubscriptionsUseCase {

    List<Post> getAllFromUserSubscriptionsInPeriod(int userId, Period period);
}
