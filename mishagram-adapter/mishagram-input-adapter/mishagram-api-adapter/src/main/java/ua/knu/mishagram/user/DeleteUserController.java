package ua.knu.mishagram.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.knu.mishagram.user.delete.DeleteUserUseCase;

@RestController
public class DeleteUserController {

    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/api/users/{id}")
    public void deleteUser(@PathVariable("id") int userId) {
        deleteUserUseCase.deleteUser(userId);
    }
}
