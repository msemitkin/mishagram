package ua.knu.mishagram.user;

import org.jetbrains.annotations.NotNull;

public class ResponseFile {
    @NotNull
    private final String name;
    @NotNull
    private final String url;
    @NotNull
    private final String type;
    private final long size;

    public ResponseFile(
        @NotNull String name,
        @NotNull String url,
        @NotNull String type,
        long size
    ) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    @NotNull
    public String getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

}
