package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Content;

import java.time.LocalDateTime;

public class PostComposite {

    private final int id;
    private final int ownerId;
    @NotNull
    private final Content content;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDateTime createDateTime;
    private final boolean isDeleted;

    public PostComposite(
        int id,
        int ownerId,
        @NotNull Content content,
        @NotNull String description,
        @NotNull LocalDateTime createDateTime,
        boolean isDeleted
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.description = description;
        this.createDateTime = createDateTime;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @NotNull
    public Content getContent() {
        return content;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
