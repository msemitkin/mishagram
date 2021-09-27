package ua.knu.mishagram.post.favourite;

import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.favourites.RemovePostFromFavouritesUseCase;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class RemovePostFromFavouritesService implements RemovePostFromFavouritesUseCase {

    private final RemovePostFromFavouritesPort removePostFromFavouritesPort;
    private final UserExistsPort userExistsPort;
    private final PostExistsPort postExistsPort;

    public RemovePostFromFavouritesService(
        RemovePostFromFavouritesPort removePostFromFavouritesPort,
        UserExistsPort userExistsPort,
        PostExistsPort postExistsPort
    ) {
        this.removePostFromFavouritesPort = removePostFromFavouritesPort;
        this.userExistsPort = userExistsPort;
        this.postExistsPort = postExistsPort;
    }

    @Override
    public void removeFromFavourites(int postId, int userId) {
        if (!userExistsPort.userExists(userId)) {
            throw new UserNotFoundException(userId, "User with given id does not exist");
        }
        if (!postExistsPort.postExists(postId)) {
            throw new PostNotFoundException(postId, "Post with given id does not exist");
        }

        removePostFromFavouritesPort.removeFromFavourites(postId, userId);
    }
}
