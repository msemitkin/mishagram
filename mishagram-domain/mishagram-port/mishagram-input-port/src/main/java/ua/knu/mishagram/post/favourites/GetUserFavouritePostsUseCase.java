package ua.knu.mishagram.post.favourites;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.util.List;

public interface GetUserFavouritePostsUseCase {

    @NotNull
    List<Post> getUserFavouritePosts(int userId);

}
