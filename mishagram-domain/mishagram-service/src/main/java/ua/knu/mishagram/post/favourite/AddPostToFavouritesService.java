package ua.knu.mishagram.post.favourite;

import org.springframework.stereotype.Service;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.favourites.AddToFavouritesUseCase;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

@Service
public class AddPostToFavouritesService implements AddToFavouritesUseCase {

    private final UserExistsPort userExistsPort;
    private final PostExistsPort postExistsPort;
    private final AddPostToFavouritesPort addPostToFavouritesPort;

    public AddPostToFavouritesService(
        UserExistsPort userExistsPort,
        PostExistsPort postExistsPort,
        AddPostToFavouritesPort addPostToFavouritesPort
    ) {
        this.userExistsPort = userExistsPort;
        this.postExistsPort = postExistsPort;
        this.addPostToFavouritesPort = addPostToFavouritesPort;
    }

    @Override
    public void addPostToFavourites(int postId, int userId) {
        if (!userExistsPort.userExists(userId)) {
            throw new UserNotFoundException("User with given id does not exist");
        }
        if (!postExistsPort.postExists(postId)) {
            throw new PostNotFoundException(postId, "Post with given id does not exist");
        }
        //TODO check if already exists
        addPostToFavouritesPort.add(postId, userId);
    }
}
