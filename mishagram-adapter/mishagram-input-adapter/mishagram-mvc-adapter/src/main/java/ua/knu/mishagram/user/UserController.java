package ua.knu.mishagram.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.knu.mishagram.User;
import ua.knu.mishagram.content.PostConverter;
import ua.knu.mishagram.post.get.GetPostUseCase;
import ua.knu.mishagram.post.get.PostComposite;
import ua.knu.mishagram.subscription.GetSubscriptionsUseCase;
import ua.knu.mishagram.user.get.GetUserUseCase;
import ua.knu.mishagram.user.get.GetUsersUseCase;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final GetPostUseCase getPostUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final GetSubscriptionsUseCase getSubscriptionsUseCase;
    private final PostConverter postConverter;

    public UserController(
        GetUserUseCase getUserUseCase,
        GetPostUseCase getPostUseCase,
        GetUsersUseCase getUsersUseCase,
        GetSubscriptionsUseCase getSubscriptionsUseCase,
        PostConverter postConverter
    ) {
        this.getUserUseCase = getUserUseCase;
        this.getPostUseCase = getPostUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.getSubscriptionsUseCase = getSubscriptionsUseCase;
        this.postConverter = postConverter;
    }

    @GetMapping("/users/{id}")
    public String getUser(
        @PathVariable("id") int userId,
        @Value("#{authenticationProvider.getUser().getId()}") int currentUserId,
        Model model
    ) {
        User user = getUserUseCase.getById(userId);
        List<PostComposite> posts = getPostUseCase.getAllByOwnerId(userId);

        List<GetPostResponse> postsResponse = posts.stream()
            .map(postConverter::toGetPostResponse).collect(Collectors.toList());

        model.addAttribute("user", user);
        model.addAttribute("posts", postsResponse);
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("isSubscribed",
            getSubscriptionsUseCase.getAllByUserId(currentUserId).stream()
                .map(User::getId)
                .anyMatch(id -> id == userId));
        return "user/userPage";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = getUsersUseCase.getAll();
        model.addAttribute("users", users);
        return "user/usersList";
    }

}
