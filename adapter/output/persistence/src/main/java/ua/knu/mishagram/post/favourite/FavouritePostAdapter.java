package ua.knu.mishagram.post.favourite;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;

import java.util.List;
import java.util.Map;

@Repository
public class FavouritePostAdapter extends JdbcRepository
    implements AddPostToFavouritesPort, LoadFavouritePostsPort, RemovePostFromFavouritesPort {

    public FavouritePostAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void add(int postId, int userId) {
        jdbcTemplate.update(
            """
                INSERT INTO favourite_post (post_id, user_id)
                VALUES (:post_id, :user_id)
                """,
            Map.of("post_id", postId, "user_id", userId)
        );
    }

    @Override
    public @NotNull List<Integer> getFavouritePostsByUserId(int userId) {
        return jdbcTemplate.queryForList(
            """
                SELECT post_id FROM favourite_post
                WHERE user_id = :user_id
                """,
            Map.of("user_id", userId),
            Integer.class
        );
    }

    @Override
    public void removeFromFavourites(int postId, int userId) {
        jdbcTemplate.update(
            """
                DELETE FROM favourite_post
                WHERE post_id = :post_id
                AND user_id = :user_id
                """,
            Map.of("post_id", postId, "user_id", userId)
        );
    }
}
