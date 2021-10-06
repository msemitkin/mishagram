package ua.knu.mishagram.post.favourites;

public interface RemovePostFromFavouritesUseCase {

    void removeFromFavourites(int postId, int userId);

}
