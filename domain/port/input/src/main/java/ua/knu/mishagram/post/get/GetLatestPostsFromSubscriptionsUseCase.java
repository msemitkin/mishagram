package ua.knu.mishagram.post.get;

import java.time.Period;
import java.util.List;

public interface GetLatestPostsFromSubscriptionsUseCase {

    List<PostComposite> getAllFromUserSubscriptionsInPeriod(int userId, Period period);
}
