package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ua.knu.mishagram.Content;

import java.util.Objects;

public class CreatePostCommand {
    private final int ownerId;
    @NotNull
    private final String description;
    @NotNull
    private final Content content;
    @Nullable
    private final Double lon;
    @Nullable
    private final Double lat;

    public CreatePostCommand(
        int ownerId,
        @NotNull String description,
        @NotNull Content content,
        @Nullable Double lon,
        @Nullable Double lat
    ) {
        this.ownerId = ownerId;
        this.description = Objects.requireNonNull(description);
        this.content = Objects.requireNonNull(content);
        this.lon = lon;
        this.lat = lat;
        validateCoordinates();
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

    @Nullable
    public Double getLon() {
        return lon;
    }

    @Nullable
    public Double getLat() {
        return lat;
    }

    private void validateCoordinates() {
        if ((lon == null && lat != null) || (lon != null && lat == null)) {
            throw new IllegalArgumentException("Coordinates are not valid");
        }
    }
}
