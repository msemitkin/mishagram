package ua.knu.mishagram.user.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.OauthProvider;
import ua.knu.mishagram.User;

import java.util.Optional;

public interface GetUserUseCase {

    @NotNull
    User getById(int id);

    @NotNull
    User getByEmail(String email);

    @NotNull
    Optional<User> getByOauthId(String oauthId);

}
