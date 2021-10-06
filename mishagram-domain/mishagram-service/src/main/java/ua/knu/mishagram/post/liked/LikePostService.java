package ua.knu.mishagram.post.liked;

import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

public class LikePostService implements LikePostUseCase {

    private final PostExistsPort postExistsPort;
    private final UserExistsPort userExistsPort;
    private final LikePostPort likePostPort;

    public LikePostService(
        PostExistsPort postExistsPort,
        UserExistsPort userExistsPort,
        LikePostPort likePostPort
    ) {
        this.postExistsPort = postExistsPort;
        this.userExistsPort = userExistsPort;
        this.likePostPort = likePostPort;
    }

    @Override
    public void likePost(int postId, int userId) {
        if (!postExistsPort.postExists(postId)) {
            throw new PostNotFoundException(postId, "Post with given id does not exist");
        }
        if (!userExistsPort.userExists(userId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        likePostPort.likePost(postId, userId);
    }
}
