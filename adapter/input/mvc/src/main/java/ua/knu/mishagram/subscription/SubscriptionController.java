package ua.knu.mishagram.subscription;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.knu.mishagram.Subscription;

@Controller
public class SubscriptionController {

    private final SubscribeUserUseCase subscribeUserUseCase;
    private final UnsubscribeUserUseCase unsubscribeUserUseCase;

    public SubscriptionController(
        SubscribeUserUseCase subscribeUserUseCase,
        UnsubscribeUserUseCase unsubscribeUserUseCase
    ) {
        this.subscribeUserUseCase = subscribeUserUseCase;
        this.unsubscribeUserUseCase = unsubscribeUserUseCase;
    }

    @PostMapping("users/{userId}/subscribe")
    public String subscribeUser(
        @Value("#{authenticationProvider.getUser().getId()}") int userId,
        @PathVariable("userId") int targetUserId
    ) {
        Subscription subscription = new Subscription(userId, targetUserId);
        subscribeUserUseCase.subscribe(subscription);
        return "redirect:/users/me/subscriptions?subscribed";
    }

    @PostMapping("users/{userId}/unsubscribe")
    public String unsubscribeUser(
        @Value("#{authenticationProvider.getUser().getId()}") int userId,
        @PathVariable("userId") int targetUserId
    ) {
        Subscription subscription = new Subscription(userId, targetUserId);
        unsubscribeUserUseCase.unsubscribe(subscription);
        return "redirect:/users/me/subscriptions?unsubscribed";
    }

}
