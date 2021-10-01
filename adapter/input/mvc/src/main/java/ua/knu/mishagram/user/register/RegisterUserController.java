package ua.knu.mishagram.user.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @GetMapping("/users/form")
    public String saveUserForm(Model model) {
        model.addAttribute("user", new RegisterUserRequest());
        return "user/registerUserForm";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
            registerUserRequest.getEmail(),
            registerUserRequest.getPassword()
        );
        registerUserUseCase.registerUser(registerUserCommand);
        return "redirect:/index";
    }
}
