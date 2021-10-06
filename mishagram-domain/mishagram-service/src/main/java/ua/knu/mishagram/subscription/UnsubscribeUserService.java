package ua.knu.mishagram.subscription;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.Subscription;

@Service
public class UnsubscribeUserService implements UnsubscribeUserUseCase {

    private final UnsubscribeUserPort unsubscribeUserPort;

    public UnsubscribeUserService(UnsubscribeUserPort unsubscribeUserPort) {
        this.unsubscribeUserPort = unsubscribeUserPort;
    }

    @Override
    public void unsubscribe(Subscription subscription) {
        unsubscribeUserPort.unsubscribe(subscription.getWhoSubscribedId(), subscription.getSubscribedToId());
    }

}
