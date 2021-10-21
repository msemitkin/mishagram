package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.ValidationException;
import ua.knu.mishagram.post.UploadFileException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

import static ua.knu.mishagram.errorhandling.ErrorReason.INVALID_FILE_ERROR;

@RestController
public class CreatePostController {

    private final CreatePostUseCase createPostUseCase;

    public CreatePostController(CreatePostUseCase createPostUseCase) {
        this.createPostUseCase = createPostUseCase;
    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(
        @Value("#{authenticationProvider.getUser().getId()}") int ownerId,
        @Valid CreatePostDto createPostDto,
        @RequestParam MultipartFile file
    ) {
        validateFile(file).ifPresent(value -> {
            throw new ValidationException("Uploaded file is invalid", INVALID_FILE_ERROR);
        });

        CreatePostCommand createPostCommand = new CreatePostCommand(
            ownerId,
            createPostDto.getDescription(),
            toContent(file)
        );

        createPostUseCase.createPost(createPostCommand);
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

    private Optional<ObjectError> validateFile(MultipartFile file) {
        if (file.isEmpty() || file.getName().isBlank()) {
            return Optional.of(new FieldError("post", "content", "Photo is required"));
        }
        return Optional.empty();
    }

}
