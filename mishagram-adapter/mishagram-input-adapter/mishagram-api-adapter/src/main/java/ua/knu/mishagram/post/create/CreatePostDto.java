package ua.knu.mishagram.post.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class CreatePostDto {

    @NotNull
    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
