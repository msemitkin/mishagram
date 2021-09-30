package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.util.List;

public class GetPostService implements GetPostUseCase {

    private final UserExistsPort userExistsPort;
    private final LoadPostPort loadPostPort;

    public GetPostService(
        UserExistsPort userExistsPort,
        LoadPostPort loadPostPort
    ) {
        this.userExistsPort = userExistsPort;
        this.loadPostPort = loadPostPort;
    }

    @Override
    public @NotNull Post getById(int id) {
        return loadPostPort.loadById(id)
            .orElseThrow(() -> new PostNotFoundException(id, "Post with given id does not exist"));
    }

    @Override
    public @NotNull List<Post> getAllByOwnerId(int ownerId) {
        if (!userExistsPort.userExists(ownerId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        return loadPostPort.loadAllByUserId(ownerId);
    }

}
