package ua.knu.mishagram.post;

import java.util.List;

public interface GetUsersLikedPostPort {

    List<Integer> getAll(int postId);

}
