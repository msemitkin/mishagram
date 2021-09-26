package ua.knu.mishagram.user;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;

import java.util.List;
import java.util.Optional;

public class UserAdapter implements LoadUserPort, SaveUserPort, UpdateUserPort {

    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NotNull Optional<User> loadById(int userId) {
        return Optional.ofNullable(userRepository.getById(userId));
    }

    @Override
    public @NotNull Optional<User> loadByEmail(@NotNull String email) {
        return Optional.ofNullable(userRepository.getByEmail(email));
    }

    @Override
    public @NotNull List<User> loadAll(List<Integer> ids) {
        return userRepository.getByIds(ids);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }
}