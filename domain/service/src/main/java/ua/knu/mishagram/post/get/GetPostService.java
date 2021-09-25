package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.util.List;

public class GetPostService implements GetPostUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPostPort loadPostPort;

    public GetPostService(
        LoadUserPort loadUserPort,
        LoadPostPort loadPostPort
    ) {
        this.loadUserPort = loadUserPort;
        this.loadPostPort = loadPostPort;
    }

    @Override
    public @NotNull Post getById(int id) {
        return loadPostPort.loadById(id)
            .orElseThrow(() -> new PostNotFoundException(id, "Post with given id does not exist"));
    }

    @Override
    public @NotNull List<Post> getByOwnerId(int ownerId) {
        loadUserPort.loadById(ownerId)
            .orElseThrow(() -> new UserNotFoundException(ownerId, "User with given id does not exist"));
        return loadPostPort.loadAllByUserId(ownerId);
    }

}
