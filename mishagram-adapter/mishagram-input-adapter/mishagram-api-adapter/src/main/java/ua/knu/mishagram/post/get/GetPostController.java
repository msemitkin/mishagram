package ua.knu.mishagram.post.get;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetPostController {

    private final GetPostUseCase getPostUseCase;

    public GetPostController(GetPostUseCase getPostUseCase) {
        this.getPostUseCase = getPostUseCase;
    }

    @GetMapping("/api/posts/{id}")
    public PostComposite getPost(@PathVariable("id") int id) {
        return getPostUseCase.getById(id);
    }

    @GetMapping("/api/users/{ownerId}/posts")
    public List<PostComposite> getUserPosts(@PathVariable("ownerId") int ownerId) {
        return getPostUseCase.getAllByOwnerId(ownerId);
    }

}
