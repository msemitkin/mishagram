package ua.knu.mishagram.subscription;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.knu.mishagram.User;

import java.util.List;

@Controller
public class GetSubscriptionsController {

    private final GetSubscriptionsUseCase getSubscriptionsUseCase;

    public GetSubscriptionsController(GetSubscriptionsUseCase getSubscriptionsUseCase) {
        this.getSubscriptionsUseCase = getSubscriptionsUseCase;
    }

    @GetMapping("/users/me/subscriptions")
    public String getSubscriptions(
        @Value("#{authenticationProvider.getUser().getId()}") int userId,
        Model model
    ) {
        List<User> subscriptions = getSubscriptionsUseCase.getAllByUserId(userId);
        model.addAttribute("subscriptions", subscriptions);
        return "subscription/subscriptions";
    }

}
