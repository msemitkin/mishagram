package ua.knu.mishagram.post.favourite;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.get.LoadPostPort;
import ua.knu.mishagram.post.favourites.GetUserFavouritePostsUseCase;

import java.util.Collections;
import java.util.List;

@Service
public class GetFavouritePostsService implements GetUserFavouritePostsUseCase {

    private final LoadFavouritePostsPort loadFavouritePostsPort;
    private final LoadPostPort loadPostPort;

    public GetFavouritePostsService(
        LoadFavouritePostsPort loadFavouritePostsPort,
        LoadPostPort loadPostPort
    ) {
        this.loadFavouritePostsPort = loadFavouritePostsPort;
        this.loadPostPort = loadPostPort;
    }

    @Override
    public @NotNull
    List<Post> getUserFavouritePosts(int userId) {
        List<Integer> postIds = loadFavouritePostsPort.getFavouritePostsByUserId(userId);
        if (postIds.isEmpty()) {
            return Collections.emptyList();
        }
        return loadPostPort.loadAllByIds(postIds);
    }

}
