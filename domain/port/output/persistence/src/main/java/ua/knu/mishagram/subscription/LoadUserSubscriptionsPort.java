package ua.knu.mishagram.subscription;

import java.util.List;

public interface LoadUserSubscriptionsPort {

    List<Integer> getAllByUserId(int userId);

}
