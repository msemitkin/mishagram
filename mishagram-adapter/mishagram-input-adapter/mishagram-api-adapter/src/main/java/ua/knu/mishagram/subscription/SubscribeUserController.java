package ua.knu.mishagram.subscription;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.knu.mishagram.Subscription;

@RestController
public class SubscribeUserController {
    private final SubscribeUserUseCase subscribeUserUseCase;
    private final UnsubscribeUserUseCase unsubscribeUserUseCase;

    public SubscribeUserController(
        SubscribeUserUseCase subscribeUserUseCase,
        UnsubscribeUserUseCase unsubscribeUserUseCase
    ) {
        this.subscribeUserUseCase = subscribeUserUseCase;
        this.unsubscribeUserUseCase = unsubscribeUserUseCase;
    }

    @PostMapping("/api/users/{userId}/subscribe")
    public void subscribeUser(
        @PathVariable("userId") int targetUserId,
        @Value("#{authenticationProvider.getUser().getId()}") int userId
    ) {
        subscribeUserUseCase.subscribe(new Subscription(userId, targetUserId));
    }

    @PostMapping("/api/users/{userId}/unsubscribe")
    public void unsubscribeUser(
        @PathVariable("userId") int targetUserId,
        @Value("#{authenticationProvider.getUser().getId()}") int userId
    ) {
        unsubscribeUserUseCase.unsubscribe(new Subscription(userId, targetUserId));
    }
}
