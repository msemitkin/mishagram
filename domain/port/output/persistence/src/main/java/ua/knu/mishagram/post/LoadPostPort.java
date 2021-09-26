package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

import java.util.List;
import java.util.Optional;

public interface LoadPostPort {

    @NotNull
    Optional<Post> loadById(int id);

    @NotNull
    List<Post> loadAll(List<Integer> ids);

    @NotNull
    List<Post> loadAllByUserId(int ownerId);

}