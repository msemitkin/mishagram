package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Point;

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
    @Nullable
    private final Point coordinates;

    public PostComposite(
        int id,
        int ownerId,
        @NotNull Content content,
        @NotNull String description,
        @NotNull LocalDateTime createDateTime,
        boolean isDeleted,
        @Nullable Point coordinates
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.content = content;
        this.description = description;
        this.createDateTime = createDateTime;
        this.isDeleted = isDeleted;
        this.coordinates = coordinates;
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

    @Nullable
    public Point getCoordinates() {
        return coordinates;
    }
}
