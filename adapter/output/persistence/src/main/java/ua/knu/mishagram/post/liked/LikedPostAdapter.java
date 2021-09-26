package ua.knu.mishagram.post.liked;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ua.knu.mishagram.JdbcRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LikedPostAdapter extends JdbcRepository
    implements CalculateLikesPort, LikePostPort, LoadUsersLikedPostPort {

    public LikedPostAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int calculateLikes(int postId) {
        Integer count = jdbcTemplate.queryForObject(
            """
                SELECT count(*) from "like" WHERE post_id = :post_id
                """,
            Map.of("post_id", postId),
            Integer.class
        );
        return Objects.requireNonNullElse(count, 0);
    }

    @Override
    public void likePost(int postId, int userId) {
        jdbcTemplate.update(
            """
                INSERT INTO "like" (post_id, user_id)
                values(:post_id, :user_id)
                """,
            Map.of("post_id", postId, "user_id", userId)
        );
    }

    @Override
    public List<Integer> getAllUsersLiked(int postId) {
        return jdbcTemplate.queryForList(
            """
                SELECT user_id FROM "like"
                WHERE post_id = :post_id 
                """,
            Map.of("post_id", postId),
            Integer.class
        );
    }
}
