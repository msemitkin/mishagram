package ua.knu.mishagram.subscription;

import ua.knu.mishagram.User;

import java.util.List;

public interface GetSubscriptionsUseCase {

    List<User> getAllByUserId(int userId);

}
