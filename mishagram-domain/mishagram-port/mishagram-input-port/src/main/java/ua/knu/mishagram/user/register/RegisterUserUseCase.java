package ua.knu.mishagram.user.register;

public interface RegisterUserUseCase {

    void registerUser(RegisterUserCommand registerUserCommand);

    void registerOauthUser(RegisterOauthUserCommand registerOauthUserCommand);
}
