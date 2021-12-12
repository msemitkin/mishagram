package ua.knu.mishagram.post.get;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.content.LoadFileContentPort;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class PostCompositeBuilderService {

    private final LoadFileContentPort loadFileContentPort;

    PostCompositeBuilderService(LoadFileContentPort loadFileContentPort) {
        this.loadFileContentPort = loadFileContentPort;
    }

    List<PostComposite> getPostComposites(List<Post> posts) {
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

    PostComposite getPostComposite(Post post) {
        return getPostComposites(Collections.singletonList(post)).get(0);
    }

    private PostComposite toPostComposite(Post post, Content content) {
        return new PostComposite(
            post.getId(),
            post.getOwnerId(),
            content,
            post.getDescription(),
            post.getCreateDateTime(),
            post.isDeleted(),
            post.getCoordinates()
        );
    }

}
