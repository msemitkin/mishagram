package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.content.LoadFileContentPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GetPostService implements GetPostUseCase {

    private final UserExistsPort userExistsPort;
    private final LoadPostPort loadPostPort;
    private final LoadFileContentPort loadFileContentPort;

    public GetPostService(
        UserExistsPort userExistsPort,
        LoadPostPort loadPostPort,
        LoadFileContentPort loadFileContentPort
    ) {
        this.userExistsPort = userExistsPort;
        this.loadPostPort = loadPostPort;
        this.loadFileContentPort = loadFileContentPort;
    }

    @Override
    public @NotNull PostComposite getById(int id) {
        Post post = loadPostPort.loadById(id)
            .orElseThrow(() -> new PostNotFoundException(id, "Post with given id does not exist"));
        Content content = loadFileContentPort.loadById(post.getContentId())
            .orElseThrow(() -> new ContentNotFoundException("Content with given id does not exist"));
        return toPostComposite(post, content);
    }

    @Override
    public @NotNull List<PostComposite> getAllByOwnerId(int ownerId) {
        if (!userExistsPort.userExists(ownerId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        List<Post> posts = loadPostPort.loadAllByUserId(ownerId);
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> contentIds = posts.stream()
            .map(Post::getContentId)
            .collect(Collectors.toList());

        Map<Integer, Content> contents = loadFileContentPort.loadAllByIds(contentIds);

        return posts.stream()
            .map(post -> toPostComposite(post, contents.get(post.getContentId())))
            .collect(Collectors.toList());

    }

    private PostComposite toPostComposite(Post post, Content content) {
        return new PostComposite(
            post.getId(),
            post.getOwnerId(),
            content,
            post.getDescription(),
            post.getCreateDateTime(),
            post.isDeleted()
        );
    }

}
