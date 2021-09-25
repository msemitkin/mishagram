package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface GetUserPostsAfterDateTimePort {

    @NotNull
    List<Post> getAll(@NotNull List<Integer> userIds, @NotNull LocalDateTime since);

}
