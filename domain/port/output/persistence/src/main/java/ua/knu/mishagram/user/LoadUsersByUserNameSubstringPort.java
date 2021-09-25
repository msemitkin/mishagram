package ua.knu.mishagram.user;

import ua.knu.mishagram.User;

import java.util.List;

public interface LoadUsersByUserNameSubstringPort {

    List<User> getAll(String substring);

}
