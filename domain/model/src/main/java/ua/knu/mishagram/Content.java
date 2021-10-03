package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

public class Content {
    private final int id;
    @NotNull
    private final String fileName;
    @NotNull
    private final String fileExtension;
    private final byte[] content;

    public Content(
        int id,
        @NotNull String fileName,
        @NotNull String fileExtension,
        byte[] content
    ) {
        this.id = id;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getFileName() {
        return fileName;
    }

    @NotNull
    public String getFileExtension() {
        return fileExtension;
    }

    public byte[] getContent() {
        return content;
    }

}
