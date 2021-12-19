package ua.knu.mishagram.user;

import ua.knu.mishagram.User;
import ua.knu.mishagram.UserOauthInfo;

public interface SaveUserPort {

    void saveUser(User user);

    void saveUserOauthInfo(UserOauthInfo userOauthInfo);

}
