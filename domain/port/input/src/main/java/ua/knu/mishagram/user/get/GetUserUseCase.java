package ua.knu.mishagram.user.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;

public interface GetUserUseCase {

    @NotNull
    User getById(int id);

}
