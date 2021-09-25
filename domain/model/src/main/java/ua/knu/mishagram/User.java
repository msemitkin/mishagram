package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

public class User {
    private final int id;
    @NotNull
    private final String email;
    private final boolean isDeleted;

    public User(
        int id,
        @NotNull String email,
        boolean isDeleted
    ) {
        this.id = id;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
