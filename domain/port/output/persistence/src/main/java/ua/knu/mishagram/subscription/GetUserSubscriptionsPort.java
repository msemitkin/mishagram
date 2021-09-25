package ua.knu.mishagram.subscription;

import java.util.List;

public interface GetUserSubscriptionsPort {

    List<Integer> getAllByUserId(int userId);

}
