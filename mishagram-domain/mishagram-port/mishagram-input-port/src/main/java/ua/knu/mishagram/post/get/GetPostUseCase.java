package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GetPostUseCase {

    @NotNull
    PostComposite getById(int id);

    @NotNull
    List<PostComposite> getAllByOwnerId(int ownerId);

}
