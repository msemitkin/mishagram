package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;

public class CreatePostCommand {
    //TODO add photos
    //TODO add category
    private final int ownerId;
    @NotNull
    private final String description;

    public CreatePostCommand(
        int ownerId,
        @NotNull String description
    ) {
        this.ownerId = ownerId;
        this.description = description;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @NotNull
    public String getDescription() {
        return description;
    }
}
