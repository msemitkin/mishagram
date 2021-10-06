package ua.knu.mishagram.user.get;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

import static java.util.function.Predicate.not;

@Service
public class GetUserService implements GetUserUseCase {

    private final LoadUserPort loadUserPort;

    public GetUserService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    @NotNull
    public User getById(int id) {
        return loadUserPort.loadById(id)
            .filter(not(User::isDeleted))
            .orElseThrow(() -> new UserNotFoundException("User with given id does not exist"));
    }

    @Override
    public @NotNull User getByEmail(String email) {
        return loadUserPort.loadByEmail(email)
            .filter(not(User::isDeleted))
            .orElseThrow(() -> new UserNotFoundException("User with given email does not exist"));
    }
}
