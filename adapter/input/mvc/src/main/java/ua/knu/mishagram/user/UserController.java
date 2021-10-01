package ua.knu.mishagram.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.User;
import ua.knu.mishagram.post.get.GetPostUseCase;
import ua.knu.mishagram.user.get.GetUserUseCase;

import java.util.List;

@Controller
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final GetPostUseCase getPostUseCase;

    public UserController(
        GetUserUseCase getUserUseCase,
        GetPostUseCase getPostUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.getPostUseCase = getPostUseCase;
    }

    @GetMapping( "/users/{id}")
    public String getUser(
        @PathVariable("id") int userId,
        Model model
    ) {
        User user = getUserUseCase.getById(userId);
        List<Post> posts = getPostUseCase.getAllByOwnerId(userId);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "user/userPage";
    }

}
