package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class CreatePostDto {

    @NotNull
    @NotBlank
    private String description;
    private Double lon;
    private Double lat;

    CreatePostDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public Double getLon() {
        return lon;
    }

    @Nullable
    public Double getLat() {
        return lat;
    }
}
