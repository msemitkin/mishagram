package ua.knu.mishagram.post.get;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;
import java.util.List;

@RestController
public class GetPostController {

    private final GetPostUseCase getPostUseCase;
    private final GetLatestPostsFromSubscriptionsUseCase getLatestPostsFromSubscriptionsUseCase;

    public GetPostController(
        GetPostUseCase getPostUseCase,
        GetLatestPostsFromSubscriptionsUseCase getLatestPostsFromSubscriptionsUseCase
    ) {
        this.getPostUseCase = getPostUseCase;
        this.getLatestPostsFromSubscriptionsUseCase = getLatestPostsFromSubscriptionsUseCase;
    }

    @GetMapping("/api/posts/{id}")
    public PostComposite getPost(@PathVariable("id") int id) {
        return getPostUseCase.getById(id);
    }

    @GetMapping("/api/users/{ownerId}/posts")
    public List<PostComposite> getUserPosts(@PathVariable("ownerId") int ownerId) {
        return getPostUseCase.getAllByOwnerId(ownerId);
    }

    @GetMapping("/api/subscriptions/posts")
    public List<PostComposite> getFromSubscriptions(
        @Value("#{authenticationProvider.getUser().getId()}") int userId
    ) {
        return getLatestPostsFromSubscriptionsUseCase
            .getAllFromUserSubscriptionsInPeriod(userId, Period.ofDays(1));
    }

}
