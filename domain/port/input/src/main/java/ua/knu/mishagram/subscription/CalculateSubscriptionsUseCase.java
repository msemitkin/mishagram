package ua.knu.mishagram.subscription;

public interface CalculateSubscriptionsUseCase {

    int calculateUsersSubscribedTo(int userId);

    int calculateUserSubscriptions(int userId);

}
