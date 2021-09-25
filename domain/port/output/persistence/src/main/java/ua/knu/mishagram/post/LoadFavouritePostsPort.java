package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface LoadFavouritePostsPort {

    @NotNull
    List<Integer> getAll(int userId);
}
