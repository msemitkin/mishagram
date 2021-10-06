package ua.knu.mishagram.post.liked;

import java.util.List;

public interface LoadUsersLikedPostPort {

    List<Integer> getAllUsersLiked(int postId);

}
