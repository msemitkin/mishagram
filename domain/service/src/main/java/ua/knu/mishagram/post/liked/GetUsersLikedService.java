package ua.knu.mishagram.post.liked;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.LoadUserPort;

import java.util.Collections;
import java.util.List;

@Service
public class GetUsersLikedService implements GetUsersLikedUseCase {

    private final PostExistsPort postExistsPort;
    private final LoadUsersLikedPostPort loadUsersLikedPostPort;
    private final LoadUserPort loadUserPort;

    public GetUsersLikedService(
        PostExistsPort postExistsPort,
        LoadUsersLikedPostPort loadUsersLikedPostPort,
        LoadUserPort loadUserPort
    ) {
        this.postExistsPort = postExistsPort;
        this.loadUsersLikedPostPort = loadUsersLikedPostPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public @NotNull List<User> getUsersLiked(int postId) {
        if (!postExistsPort.postExists(postId)) {
            throw new PostNotFoundException(postId, "Post with given id does not exist");
        }
        List<Integer> userIds = loadUsersLikedPostPort.getAllUsersLiked(postId);
        if (userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return loadUserPort.loadAll(userIds);
    }
}
