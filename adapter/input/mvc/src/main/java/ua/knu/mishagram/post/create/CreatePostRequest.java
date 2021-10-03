package ua.knu.mishagram.post.create;

import org.springframework.web.multipart.MultipartFile;

class CreatePostRequest {
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
