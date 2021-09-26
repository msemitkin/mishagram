package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface LoadUserPostsAfterDateTimePort {

    @NotNull
    List<Post> getAll(@NotNull List<Integer> userIds, @NotNull LocalDateTime since);

}
