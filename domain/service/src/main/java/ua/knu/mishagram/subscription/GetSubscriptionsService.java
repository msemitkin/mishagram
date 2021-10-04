package ua.knu.mishagram.subscription;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUserPort;

import java.util.Collections;
import java.util.List;

@Service
public class GetSubscriptionsService implements GetSubscriptionsUseCase {

    private final LoadUserSubscriptionsPort loadUserSubscriptionsPort;
    private final LoadUserPort loadUserPort;

    public GetSubscriptionsService(
        LoadUserSubscriptionsPort loadUserSubscriptionsPort,
        LoadUserPort loadUserPort
    ) {
        this.loadUserSubscriptionsPort = loadUserSubscriptionsPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public List<User> getAllByUserId(int userId) {
        List<Integer> userIds = loadUserSubscriptionsPort.getAllByUserId(userId);
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return loadUserPort.loadAll(userIds);
    }

}
