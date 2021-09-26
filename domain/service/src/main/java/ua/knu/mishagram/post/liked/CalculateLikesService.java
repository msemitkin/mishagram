package ua.knu.mishagram.post.liked;

import ua.knu.mishagram.post.CalculateLikesPort;
import ua.knu.mishagram.post.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;

public class CalculateLikesService implements CalculateLikesUseCase {

    private final LoadPostPort loadPostPort;
    private final CalculateLikesPort calculateLikesPort;

    public CalculateLikesService(
        LoadPostPort loadPostPort,
        CalculateLikesPort calculateLikesPort
    ) {
        this.loadPostPort = loadPostPort;
        this.calculateLikesPort = calculateLikesPort;
    }

    @Override
    public int calculateLikes(int postId) {
        loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));
        return calculateLikesPort.calculate(postId);
    }
}
