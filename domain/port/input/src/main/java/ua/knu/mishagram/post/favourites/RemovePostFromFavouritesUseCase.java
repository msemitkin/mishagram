package ua.knu.mishagram.post.favourites;

public interface RemovePostFromFavouritesUseCase {

    void remove(int postId, int userId);

}
