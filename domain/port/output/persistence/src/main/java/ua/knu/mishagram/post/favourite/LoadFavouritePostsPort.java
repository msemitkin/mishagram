package ua.knu.mishagram.post.favourite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface LoadFavouritePostsPort {

    @NotNull
    List<Integer> getAllByUserId(int userId);
}
