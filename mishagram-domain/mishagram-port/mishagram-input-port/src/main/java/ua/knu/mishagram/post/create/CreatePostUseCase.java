package ua.knu.mishagram.post.create;

import org.jetbrains.annotations.NotNull;

public interface CreatePostUseCase {

    void createPost(@NotNull CreatePostCommand createPostCommand);

}
