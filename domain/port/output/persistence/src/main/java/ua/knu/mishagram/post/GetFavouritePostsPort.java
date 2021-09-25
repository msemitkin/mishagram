package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.util.List;

public interface GetFavouritePostsPort {

    @NotNull
    List<Post> getAllByUserId(int userId);
}
