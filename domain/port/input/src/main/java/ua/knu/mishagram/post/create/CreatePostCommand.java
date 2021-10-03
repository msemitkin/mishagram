package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Content;

public class CreatePostCommand {
    //TODO add photo
    private final int ownerId;
    @NotNull
    private final String description;
    @NotNull
    private final Content content;

    public CreatePostCommand(
        int ownerId,
        @NotNull String description,
        Content content
    ) {
        this.ownerId = ownerId;
        this.description = description;
        this.content = content;
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
