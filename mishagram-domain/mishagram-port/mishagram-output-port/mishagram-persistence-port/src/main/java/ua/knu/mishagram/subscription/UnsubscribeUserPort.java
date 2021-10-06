package ua.knu.mishagram.subscription;

public interface UnsubscribeUserPort {

    void unsubscribe(int userId, int targetUserId);

}
