package ua.knu.mishagram.user.get;

import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadUsersByUserNameSubstringPort;

import java.util.List;

public class GetUsersByUsernameSubstringService implements GetUsersByUsernameSubStringUseCase {

    private final LoadUsersByUserNameSubstringPort loadUsersByUserNameSubstringPort;

    public GetUsersByUsernameSubstringService(
        LoadUsersByUserNameSubstringPort loadUsersByUserNameSubstringPort
    ) {
        this.loadUsersByUserNameSubstringPort = loadUsersByUserNameSubstringPort;
    }

    @Override
    public List<User> getAll(String substring) {
        //TODO sort by match score
        return loadUsersByUserNameSubstringPort.getAll(substring);
    }
}
