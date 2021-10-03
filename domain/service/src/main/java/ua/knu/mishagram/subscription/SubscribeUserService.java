package ua.knu.mishagram.subscription;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.Subscription;

import java.util.List;

@Service
public class SubscribeUserService implements SubscribeUserUseCase {

    private final SubscribeUserPost subscribeUserPost;
    private final LoadUserSubscriptionsPort loadUserSubscriptionsPort;

    public SubscribeUserService(
        SubscribeUserPost subscribeUserPost,
        LoadUserSubscriptionsPort loadUserSubscriptionsPort
    ) {
        this.subscribeUserPost = subscribeUserPost;
        this.loadUserSubscriptionsPort = loadUserSubscriptionsPort;
    }

    @Override
    public void subscribe(Subscription subscription) {
        if(subscription.getWhoSubscribedId() == subscription.getSubscribedToId()) {
            throw new InvalidSubscriptionException("Cannot subscribe to self");
        }
        List<Integer> userSubscriptions = loadUserSubscriptionsPort.getAllByUserId(subscription.getWhoSubscribedId());
        if(!userSubscriptions.contains(subscription.getSubscribedToId())) {
            subscribeUserPost.subscribe(subscription.getWhoSubscribedId(), subscription.getSubscribedToId());
        }
    }

}
