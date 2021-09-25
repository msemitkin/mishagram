package ua.knu.mishagram.user.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class GetUserService implements GetUserUseCase {

    private final LoadUserPort loadUserPort;

    public GetUserService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    @NotNull
    public User getById(int id) {
        return loadUserPort.loadById(id)
            .orElseThrow(() -> new UserNotFoundException(id, "User with given id does not exist"));
    }

}
