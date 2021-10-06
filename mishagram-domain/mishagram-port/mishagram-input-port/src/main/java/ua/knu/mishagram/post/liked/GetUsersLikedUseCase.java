package ua.knu.mishagram.post.liked;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.User;

import java.util.List;

public interface GetUsersLikedUseCase {

    @NotNull
    List<User> getUsersLiked(int postId);

}
