package ua.knu.mishagram.user.register;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.OauthProvider;

public class RegisterOauthUserCommand {

    @NotNull
    private final String oauthId;
    @NotNull
    private final OauthProvider oauthProvider;
    @NotNull
    private final String email;

    public RegisterOauthUserCommand(
        @NotNull String oauthId,
        @NotNull OauthProvider oauthProvider,
        @NotNull String email
    ) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.email = email;
    }

    @NotNull
    public String getOauthId() {
        return oauthId;
    }

    @NotNull
    public OauthProvider getOauthProvider() {
        return oauthProvider;
    }

    @NotNull
    public String getEmail() {
        return email;
    }
}
