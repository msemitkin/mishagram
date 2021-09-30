package ua.knu.mishagram.user.delete;

import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UpdateUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class DeleteUserService implements DeleteUserUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;

    public DeleteUserService(
        LoadUserPort loadUserPort,
        UpdateUserPort updateUserPort
    ) {
        this.loadUserPort = loadUserPort;
        this.updateUserPort = updateUserPort;
    }

    @Override
    public void deleteUser(int userId) {
        User user = loadUserPort.loadById(userId)
            .orElseThrow(() -> new UserNotFoundException("User with given id does not exist"));
        if (user.isDeleted()) {
            return;
        }
        User deletedUser = new User(user.getId(), user.getEmail(), true, user.getRegisteredDateTime());
        updateUserPort.update(deletedUser);
    }
}
