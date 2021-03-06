package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public class User {
    private final int id;
    @NotNull
    private final String email;
    private final boolean isDeleted;
    @NotNull
    private final LocalDateTime registeredDateTime;
    @NotNull
    private final String passwordHash;

    public User(
        int id,
        @NotNull String email,
        boolean isDeleted,
        @NotNull LocalDateTime registeredDateTime,
        @NotNull String passwordHash
    ) {
        this.id = id;
        this.email = email;
        this.isDeleted = isDeleted;
        this.registeredDateTime = registeredDateTime;
        this.passwordHash = passwordHash;
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

    @NotNull
    public LocalDateTime getRegisteredDateTime() {
        return registeredDateTime;
    }

    @NotNull
    public String getPasswordHash() {
        return passwordHash;
    }
}
