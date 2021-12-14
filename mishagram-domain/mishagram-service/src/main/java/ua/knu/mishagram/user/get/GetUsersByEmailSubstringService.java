package ua.knu.mishagram.user.get;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUsersByEmailSubstringPort;

import java.util.Collections;
import java.util.List;

@Service
public class GetUsersByEmailSubstringService implements GetUsersByEmailSubStringUseCase {

    private final LoadUsersByEmailSubstringPort loadUsersByEmailSubstringPort;

    public GetUsersByEmailSubstringService(
        LoadUsersByEmailSubstringPort loadUsersByEmailSubstringPort
    ) {
        this.loadUsersByEmailSubstringPort = loadUsersByEmailSubstringPort;
    }

    @Override
    public List<User> getBySubstring(String substring) {
        if (substring.length() < 3) {
            return Collections.emptyList();
        }
        return loadUsersByEmailSubstringPort.getByEmailSubstring(substring);
    }
}
