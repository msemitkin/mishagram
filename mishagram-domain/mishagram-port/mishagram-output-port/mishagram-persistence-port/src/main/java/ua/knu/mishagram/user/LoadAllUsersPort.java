package ua.knu.mishagram.user;

import ua.knu.mishagram.User;

import java.util.List;

public interface LoadAllUsersPort {

    List<User> loadAll();
}
