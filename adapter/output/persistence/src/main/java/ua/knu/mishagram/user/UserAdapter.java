package ua.knu.mishagram.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserAdapter implements LoadUserPort, SaveUserPort, UpdateUserPort, UserExistsPort {

    private final UserRepository userRepository;

    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NotNull Optional<User> loadById(int userId) {
        return userRepository.getById(userId);
    }

    @Override
    public @NotNull Optional<User> loadByEmail(@NotNull String email) {
        return userRepository.getByEmail(email);
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

    @Override
    public boolean userExists(int userId) {
        return userRepository.userExists(userId);
    }
}
