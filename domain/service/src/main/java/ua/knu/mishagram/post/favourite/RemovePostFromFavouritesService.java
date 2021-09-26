package ua.knu.mishagram.post.favourite;

import ua.knu.mishagram.post.get.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.favourites.RemovePostFromFavouritesUseCase;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class RemovePostFromFavouritesService implements RemovePostFromFavouritesUseCase {

    private final RemovePostFromFavouritesPort removePostFromFavouritesPort;
    private final LoadUserPort loadUserPort;
    private final LoadPostPort loadPostPort;

    public RemovePostFromFavouritesService(
        RemovePostFromFavouritesPort removePostFromFavouritesPort,
        LoadUserPort loadUserPort,
        LoadPostPort loadPostPort
    ) {
        this.removePostFromFavouritesPort = removePostFromFavouritesPort;
        this.loadUserPort = loadUserPort;
        this.loadPostPort = loadPostPort;
    }

    @Override
    public void remove(int postId, int userId) {
        loadUserPort.loadById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId, "User with given id does not exist"));
        loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));
        removePostFromFavouritesPort.removeFromFavourites(postId, userId);
    }
}
