package ua.knu.mishagram.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.knu.mishagram.User;
import ua.knu.mishagram.post.get.GetPostUseCase;
import ua.knu.mishagram.post.get.PostComposite;
import ua.knu.mishagram.subscription.GetSubscriptionsUseCase;
import ua.knu.mishagram.user.get.GetUserUseCase;
import ua.knu.mishagram.user.get.GetUsersUseCase;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final GetPostUseCase getPostUseCase;
    private final GetUsersUseCase getUsersUseCase;
    private final GetSubscriptionsUseCase getSubscriptionsUseCase;

    public UserController(
        GetUserUseCase getUserUseCase,
        GetPostUseCase getPostUseCase,
        GetUsersUseCase getUsersUseCase,
        GetSubscriptionsUseCase getSubscriptionsUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.getPostUseCase = getPostUseCase;
        this.getUsersUseCase = getUsersUseCase;
        this.getSubscriptionsUseCase = getSubscriptionsUseCase;
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
            .map(post -> {
                String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(post.getId()))
                    .toUriString();

                return toGetPostResponse(post);
            }).collect(Collectors.toList());

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

//    private GetPostResponse toGetPostResponse(PostComposite postComposite, String downloadUri) {
//        return new GetPostResponse(
//            postComposite.getId(),
//            postComposite.getOwnerId(),
//            new ResponseFile(
//                postComposite.getContent().getFileName(),
//                downloadUri,
//                postComposite.getContent().getFileExtension(),
//                postComposite.getContent().getContent().length
//            ),
//            postComposite.getDescription(),
//            postComposite.getCreateDateTime(),
//            postComposite.isDeleted()
//        );
//    }

    private GetPostResponse toGetPostResponse(PostComposite postComposite) {
        return new GetPostResponse(
            postComposite.getId(),
            postComposite.getOwnerId(),
            "data:image/png;base64," + Base64.getEncoder().encodeToString(postComposite.getContent().getContent()),
            postComposite.getDescription(),
            postComposite.getCreateDateTime(),
            postComposite.isDeleted()
        );
    }

}
