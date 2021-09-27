package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.util.List;

public interface GetPostUseCase {

    @NotNull
    Post getById(int id);

    @NotNull
    List<Post> getAllByOwnerId(int ownerId);

}
