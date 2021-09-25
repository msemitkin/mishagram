package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.SavePostPort;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;

public class CreatePostService implements CreatePostUseCase {

    private final SavePostPort savePostPort;
    private final LoadUserPort loadUserPort;

    public CreatePostService(
        SavePostPort savePostPort,
        LoadUserPort loadUserPort
    ) {
        this.savePostPort = savePostPort;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public void createPost(@NotNull CreatePostCommand createPostCommand) {
        Post post = mapToPost(createPostCommand);
        int ownerId = post.getOwnerId();
        if (loadUserPort.loadById(ownerId).isEmpty()) {
            throw new UserNotFoundException(ownerId, "User with given id does not exist");
        }
        savePostPort.save(post);
    }

    private Post mapToPost(CreatePostCommand createPostCommand) {
        return new Post(
            0,
            createPostCommand.getOwnerId(),
            createPostCommand.getDescription(),
            LocalDateTime.now(),
             false
        );
    }

}
