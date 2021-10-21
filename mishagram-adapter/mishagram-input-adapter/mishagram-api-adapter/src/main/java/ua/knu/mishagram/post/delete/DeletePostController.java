package ua.knu.mishagram.post.delete;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeletePostController {

    private final DeletePostUseCase deletePostUseCase;

    public DeletePostController(DeletePostUseCase deletePostUseCase) {
        this.deletePostUseCase = deletePostUseCase;
    }

    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable("id") int postId) {
        deletePostUseCase.deletePostById(postId);
    }
}
