package ua.knu.mishagram.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ua.knu.mishagram.Point;

import java.time.LocalDateTime;

public class GetPostResponse {
    private final int id;
    private final int ownerId;
    @NotNull
    private final String base64Img;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDateTime createDateTime;
    private final boolean isDeleted;
    @Nullable
    private final Point coordinates;

    public GetPostResponse(
        int id,
        int ownerId,
        @NotNull String base64Img,
        @NotNull String description,
        @NotNull LocalDateTime createDateTime,
        boolean isDeleted,
        @Nullable Point coordinates
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.base64Img = base64Img;
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
    public String getBase64Img() {
        return base64Img;
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
