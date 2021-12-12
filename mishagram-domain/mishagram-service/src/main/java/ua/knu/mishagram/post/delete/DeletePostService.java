package ua.knu.mishagram.post.delete;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.get.LoadPostPort;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.UpdatePostPort;

@Service
public class DeletePostService implements DeletePostUseCase {

    private final LoadPostPort loadPostPort;
    private final UpdatePostPort updatePostPort;

    public DeletePostService(
        LoadPostPort loadPostPort,
        UpdatePostPort updatePostPort
    ) {
        this.loadPostPort = loadPostPort;
        this.updatePostPort = updatePostPort;
    }


    @Override
    public void deletePostById(int postId) {
        Post post = loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));

        Post deletedPost = new Post(
            post.getId(),
            post.getOwnerId(),
            post.getContentId(),
            post.getDescription(),
            post.getCreateDateTime(),
            true,
            post.getCoordinates()
        );

        updatePostPort.update(deletedPost);
    }
}
