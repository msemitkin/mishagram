package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

import java.util.List;

@Service
public class GetPostService implements GetPostUseCase {

    private final UserExistsPort userExistsPort;
    private final LoadPostPort loadPostPort;
    private final PostCompositeBuilderService postCompositeBuilderService;


    public GetPostService(
        UserExistsPort userExistsPort,
        LoadPostPort loadPostPort,
        PostCompositeBuilderService postCompositeBuilderService
    ) {
        this.userExistsPort = userExistsPort;
        this.loadPostPort = loadPostPort;
        this.postCompositeBuilderService = postCompositeBuilderService;
    }

    @Override
    public @NotNull PostComposite getById(int id) {
        Post post = loadPostPort.loadById(id)
            .orElseThrow(() -> new PostNotFoundException(id, "Post with given id does not exist"));
        return postCompositeBuilderService.getPostComposite(post);
    }

    @Override
    public @NotNull List<PostComposite> getAllByOwnerId(int ownerId) {
        if (!userExistsPort.userExists(ownerId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        List<Post> posts = loadPostPort.loadAllByUserId(ownerId);
        return postCompositeBuilderService.getPostComposites(posts);
    }

}
