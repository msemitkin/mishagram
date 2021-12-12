package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

public class Post {

    private final int id;
    private final int ownerId;
    private final int contentId;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDateTime createDateTime;
    private final boolean isDeleted;
    @Nullable
    private final Point coordinates;

    public Post(
        int id,
        int ownerId,
        int contentId,
        @NotNull String description,
        @NotNull LocalDateTime createDateTime,
        boolean isDeleted,
        @Nullable Point coordinates
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.contentId = contentId;
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

    public int getContentId() {
        return contentId;
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
