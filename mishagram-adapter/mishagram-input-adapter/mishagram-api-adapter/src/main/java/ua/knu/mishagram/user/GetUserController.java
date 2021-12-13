package ua.knu.mishagram.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.get.GetUserUseCase;
import ua.knu.mishagram.user.get.GetUsersUseCase;

import java.util.List;


@RestController
public class GetUserController {

    private final GetUserUseCase getUserUseCase;
    private final GetUsersUseCase getUsersUseCase;

    public GetUserController(
        GetUserUseCase getUserUseCase,
        GetUsersUseCase getUsersUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.getUsersUseCase = getUsersUseCase;
    }

    @GetMapping("/api/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return getUserUseCase.getById(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return getUsersUseCase.getAll();
    }

}
