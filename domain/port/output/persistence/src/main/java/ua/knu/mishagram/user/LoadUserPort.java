package ua.knu.mishagram.user;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;

import java.util.Optional;

public interface LoadUserPort {

    @NotNull
    Optional<User> loadById(int userId);

    @NotNull
    Optional<User> loadByEmail(@NotNull String email);

}
