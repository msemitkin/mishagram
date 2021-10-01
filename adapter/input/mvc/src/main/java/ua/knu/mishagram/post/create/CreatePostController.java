package ua.knu.mishagram.post.create;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CreatePostController {

    private final CreatePostUseCase createPostUseCase;

    public CreatePostController(CreatePostUseCase createPostUseCase) {
        this.createPostUseCase = createPostUseCase;
    }

    @GetMapping("/posts/form")
    public String getPostForm(Model model) {
        model.addAttribute("post", new CreatePostRequest());
        return "post/createPostForm";
    }

    @PostMapping("/posts")
    public String createPost(
        @ModelAttribute("post") CreatePostRequest createPostRequest,
        @RequestParam("ownerId") int ownerId //TODO fetch id from authentication
    ) {
        CreatePostCommand createPostCommand = new CreatePostCommand(ownerId, createPostRequest.getDescription());
        createPostUseCase.createPost(createPostCommand);
        return "redirect:index";
    }
}
