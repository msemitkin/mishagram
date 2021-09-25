package ua.knu.mishagram.user.get;

import ua.knu.mishagram.User;

import java.util.List;

public interface GetUserByUsernameSubStringUseCase {

    List<User> getAll(String substring);
}
