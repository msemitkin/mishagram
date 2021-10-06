package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Content;

import java.util.Objects;

public class CreatePostCommand {
    private final int ownerId;
    @NotNull
    private final String description;
    @NotNull
    private final Content content;

    public CreatePostCommand(
        int ownerId,
        @NotNull String description,
        @NotNull Content content
    ) {
        this.ownerId = ownerId;
        this.description = Objects.requireNonNull(description);
        this.content = Objects.requireNonNull(content);
    }

    public int getOwnerId() {
        return ownerId;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public Content getContent() {
        return content;
    }
}
