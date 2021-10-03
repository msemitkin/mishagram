package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ua.knu.mishagram.Content;

import java.io.IOException;

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
        @Value("#{authenticationProvider.getUser().getId()}") int ownerId
    ) {
        CreatePostCommand createPostCommand = new CreatePostCommand(
            ownerId, createPostRequest.getDescription(), toContent(createPostRequest.getContent())
        );
        createPostUseCase.createPost(createPostCommand);
        return "redirect:index";
    }

    @NotNull
    private Content toContent(
        MultipartFile multipartFile
    ) {
        try {
            return new Content(
                0,
                StringUtils.cleanPath(multipartFile.getName()),
                multipartFile.getContentType(),
                multipartFile.getBytes()
            );
        } catch (IOException e) {
            throw new UploadFileException("Failed to upload file");
        }
    }
}
