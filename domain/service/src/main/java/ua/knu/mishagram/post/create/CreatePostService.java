package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.SavePostPort;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class CreatePostService implements CreatePostUseCase {

    private final SavePostPort savePostPort;
    private final UserExistsPort userExistsPort;
    private final DateTimeProvider dateTimeProvider;

    public CreatePostService(
        SavePostPort savePostPort,
        UserExistsPort userExistsPort,
        DateTimeProvider dateTimeProvider
    ) {
        this.savePostPort = savePostPort;
        this.userExistsPort = userExistsPort;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void createPost(@NotNull CreatePostCommand createPostCommand) {
        Post post = mapToPost(createPostCommand);
        int ownerId = post.getOwnerId();
        if (!userExistsPort.userExists(ownerId)) {
            throw new UserNotFoundException(ownerId, "User with given id does not exist");
        }
        savePostPort.save(post);
    }

    private Post mapToPost(CreatePostCommand createPostCommand) {
        return new Post(
            0,
            createPostCommand.getOwnerId(),
            createPostCommand.getDescription(),
            dateTimeProvider.now(),
            false
        );
    }

}
