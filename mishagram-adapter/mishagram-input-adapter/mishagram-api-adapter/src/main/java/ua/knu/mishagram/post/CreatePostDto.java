package ua.knu.mishagram.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreatePostDto {

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
