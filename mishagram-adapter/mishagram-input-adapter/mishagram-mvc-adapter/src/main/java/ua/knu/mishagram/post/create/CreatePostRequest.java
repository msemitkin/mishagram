package ua.knu.mishagram.post.create;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class CreatePostRequest {
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    private String description;
    private MultipartFile content;

    CreatePostRequest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile content) {
        this.content = content;
    }
}
