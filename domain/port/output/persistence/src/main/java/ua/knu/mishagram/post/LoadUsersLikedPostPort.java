package ua.knu.mishagram.post;

import java.util.List;

public interface LoadUsersLikedPostPort {

    List<Integer> getAll(int postId);

}
