package ua.knu.mishagram.post.favourite;

import ua.knu.mishagram.post.get.LoadPostPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.favourites.AddToFavouritesUseCase;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

public class AddPostToFavouritesService implements AddToFavouritesUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadPostPort loadPostPort;
    private final AddPostToFavouritesPort addPostToFavouritesPort;

    public AddPostToFavouritesService(
        LoadUserPort loadUserPort,
        LoadPostPort loadPostPort,
        AddPostToFavouritesPort addPostToFavouritesPort
    ) {
        this.loadUserPort = loadUserPort;
        this.loadPostPort = loadPostPort;
        this.addPostToFavouritesPort = addPostToFavouritesPort;
    }

    @Override
    public void addToFavouritesUseCase(int postId, int userId) {
        loadUserPort.loadById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId, "User with given id does not exist"));
        loadPostPort.loadById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId, "Post with given id does not exist"));
        //TODO check if already exists
        addPostToFavouritesPort.add(postId, userId);
    }
}
