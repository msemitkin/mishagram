package ua.knu.mishagram.user.register;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;

    public RegisterUserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/api/users")
    public void registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(
            registerUserDTO.getEmail(), registerUserDTO.getPassword());
        registerUserUseCase.registerUser(registerUserCommand);
    }

}
