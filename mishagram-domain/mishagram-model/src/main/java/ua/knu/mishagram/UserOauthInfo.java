package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

public class UserOauthInfo {
    private final int userId;
    @NotNull
    private final String oauthId;
    @NotNull
    private final OauthProvider oauthProvider;

    public UserOauthInfo(
        int userId,
        @NotNull String oauthId,
        @NotNull OauthProvider oauthProvider
    ) {
        this.userId = userId;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public int getUserId() {
        return userId;
    }

    @NotNull
    public String getOauthId() {
        return oauthId;
    }

    @NotNull
    public OauthProvider getOauthProvider() {
        return oauthProvider;
    }
}
