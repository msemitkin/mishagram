package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import ua.knu.mishagram.Post;

public interface UpdatePostPort {

    void update(@NotNull Post post);
}
