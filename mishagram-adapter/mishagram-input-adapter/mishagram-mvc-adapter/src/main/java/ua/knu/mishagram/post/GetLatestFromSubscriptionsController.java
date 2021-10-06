package ua.knu.mishagram.post;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.knu.mishagram.content.PostConverter;
import ua.knu.mishagram.post.get.GetLatestPostsFromSubscriptionsUseCase;
import ua.knu.mishagram.user.GetPostResponse;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GetLatestFromSubscriptionsController {

    private final GetLatestPostsFromSubscriptionsUseCase getLatestPostsFromSubscriptionsUseCase;
    private final PostConverter postConverter;

    public GetLatestFromSubscriptionsController(
        GetLatestPostsFromSubscriptionsUseCase getLatestPostsFromSubscriptionsUseCase,
        PostConverter postConverter
    ) {
        this.getLatestPostsFromSubscriptionsUseCase = getLatestPostsFromSubscriptionsUseCase;
        this.postConverter = postConverter;
    }

    @GetMapping("/subscriptions/latest")
    public String getLatestFromSubscriptions(
        @Value("#{authenticationProvider.getUser().getId()}") int userId,
        Model model
        ) {
        List<GetPostResponse> posts = getLatestPostsFromSubscriptionsUseCase
            .getAllFromUserSubscriptionsInPeriod(userId, Period.ofDays(1))
            .stream()
            .map(postConverter::toGetPostResponse)
            .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "post/latestFromSubscriptions";
    }
}
