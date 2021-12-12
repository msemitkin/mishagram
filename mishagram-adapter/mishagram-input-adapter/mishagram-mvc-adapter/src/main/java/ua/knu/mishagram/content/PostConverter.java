package ua.knu.mishagram.content;

import org.springframework.stereotype.Component;
import ua.knu.mishagram.post.get.PostComposite;
import ua.knu.mishagram.user.GetPostResponse;

import java.util.Base64;

@Component
public class PostConverter {

    public GetPostResponse toGetPostResponse(PostComposite postComposite) {
        byte[] byteContent = postComposite.getContent().getContent();
        return new GetPostResponse(
            postComposite.getId(),
            postComposite.getOwnerId(),
            "data:image/png;base64," + Base64.getEncoder().encodeToString(byteContent),
            postComposite.getDescription(),
            postComposite.getCreateDateTime(),
            postComposite.isDeleted(),
            postComposite.getCoordinates()
        );
    }
}
