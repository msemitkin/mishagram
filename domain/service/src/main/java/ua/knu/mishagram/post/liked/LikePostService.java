package ua.knu.mishagram.post.liked;

import ua.knu.mishagram.post.LikePostPort;
import ua.knu.mishagram.post.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class LikePostService implements LikePostUseCase {

    private final LoadPostPort loadPostPort;
    private final LoadUserPort loadUserPort;
    private final LikePostPort likePostPort;

    public LikePostService(
        LoadPostPort loadPostPort,
        LoadUserPort loadUserPort,
        LikePostPort likePostPort
    ) {
        this.loadPostPort = loadPostPort;
        this.loadUserPort = loadUserPort;
        this.likePostPort = likePostPort;
    }

    @Override
    public void likePost(int postId, int userId) {
        loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));
        loadUserPort.loadById(userId)
            .orElseThrow(() -> new UserNotFoundException(postId, "User with given id does not exist"));

        likePostPort.likePost(postId, userId);
    }
}
