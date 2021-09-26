package ua.knu.mishagram.post.liked;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;
import ua.knu.mishagram.post.GetUsersLikedPostPort;
import ua.knu.mishagram.post.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.LoadUserPort;

import java.util.List;

public class GetUsersLikedService implements GetUsersLikedUseCase {

    private final LoadPostPort loadPostPort;
    private final GetUsersLikedPostPort getUsersLikedPostPort;
    private final LoadUserPort loadUserPort;

    public GetUsersLikedService(
        LoadPostPort loadPostPort,
        GetUsersLikedPostPort getUsersLikedPostPort,
        LoadUserPort loadUserPort
    ) {
        this.loadPostPort = loadPostPort;
        this.getUsersLikedPostPort = getUsersLikedPostPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public @NotNull List<User> getUsersLiked(int postId) {
        loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));
        List<Integer> userIds = getUsersLikedPostPort.getAll(postId);
        return loadUserPort.loadAll(userIds);
    }
}
