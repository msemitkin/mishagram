package ua.knu.mishagram.user.get;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.LoadAllUsersPort;

import java.util.List;

@Service
public class GetUsersService implements GetUsersUseCase {

    private  final LoadAllUsersPort loadAllUsersPort;

    public GetUsersService(LoadAllUsersPort loadAllUsersPort) {
        this.loadAllUsersPort = loadAllUsersPort;
    }

    @Override
    public List<User> getAll() {
        return loadAllUsersPort.loadAll();
    }

}
