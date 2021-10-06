package ua.knu.mishagram.post.liked;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;

@Service
public class CalculateLikesService implements CalculateLikesUseCase {

    private final PostExistsPort postExistsPort;
    private final CalculateLikesPort calculateLikesPort;

    public CalculateLikesService(
        PostExistsPort postExistsPort,
        CalculateLikesPort calculateLikesPort
    ) {
        this.postExistsPort = postExistsPort;
        this.calculateLikesPort = calculateLikesPort;
    }

    @Override
    public int calculateLikes(int postId) {
        if (!postExistsPort.postExists(postId)) {
            throw new PostNotFoundException(postId, "Post with given id does not exist");
        }
        return calculateLikesPort.calculateLikes(postId);
    }
}
