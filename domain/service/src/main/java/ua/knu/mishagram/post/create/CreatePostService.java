package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.SavePostContentPort;
import ua.knu.mishagram.post.SavePostPort;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

@Service
public class CreatePostService implements CreatePostUseCase {

    private final SavePostPort savePostPort;
    private final UserExistsPort userExistsPort;
    private final DateTimeProvider dateTimeProvider;
    private final SavePostContentPort savePostContentPort;

    public CreatePostService(
        SavePostPort savePostPort,
        UserExistsPort userExistsPort,
        DateTimeProvider dateTimeProvider,
        SavePostContentPort savePostContentPort
    ) {
        this.savePostPort = savePostPort;
        this.userExistsPort = userExistsPort;
        this.dateTimeProvider = dateTimeProvider;
        this.savePostContentPort = savePostContentPort;
    }

    @Override
    @Transactional
    public void createPost(@NotNull CreatePostCommand createPostCommand) {
        int ownerId = createPostCommand.getOwnerId();
        if (!userExistsPort.userExists(ownerId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        Content content = createPostCommand.getContent();
        int contentId = savePostContentPort.savePostContent(content);
        Post post = mapToPost(createPostCommand, contentId);
        savePostPort.save(post);
    }

    private Post mapToPost(CreatePostCommand createPostCommand, int contentId) {
        return new Post(
            0,
            createPostCommand.getOwnerId(),
            contentId,
            createPostCommand.getDescription(),
            dateTimeProvider.now(),
            false
        );
    }

}
