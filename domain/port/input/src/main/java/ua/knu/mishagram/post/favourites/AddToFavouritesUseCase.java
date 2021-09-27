package ua.knu.mishagram.post.favourites;

public interface AddToFavouritesUseCase {

    void addPostToFavourites(int postId, int userId);
}
