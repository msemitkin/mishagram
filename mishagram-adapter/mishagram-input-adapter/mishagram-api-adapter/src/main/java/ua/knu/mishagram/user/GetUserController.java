package ua.knu.mishagram.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.get.GetUserUseCase;

@RestController
public class GetUserController {

    private final GetUserUseCase getUserUseCase;

    public GetUserController(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return getUserUseCase.getById(id);
    }

}
