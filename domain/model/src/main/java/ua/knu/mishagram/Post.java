package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class Post {

    private final int id;
    private final int ownerId;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDateTime createDateTime;
    private final boolean isActive;

    public Post(
        int id,
        int ownerId,
        @NotNull String description,
        @NotNull LocalDateTime createDateTime,
        boolean isActive
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.description = description;
        this.createDateTime = createDateTime;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public boolean isActive() {
        return isActive;
    }
}
