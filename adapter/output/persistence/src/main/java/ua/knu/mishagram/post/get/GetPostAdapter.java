package ua.knu.mishagram.post.get;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;
import ua.knu.mishagram.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GetPostAdapter extends JdbcRepository implements LoadPostPort, LoadUserPostsAfterDateTimePort {

    public GetPostAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public @NotNull Optional<Post> loadById(int id) {
        return Optional.ofNullable(
            jdbcTemplate.queryForObject(
                """
                    SELECT * FROM "post" WHERE id = :id
                    """,
                Map.of("id", id),
                getPostRowMapper()
            )
        );
    }

    @Override
    public @NotNull List<Post> loadAllByIds(List<Integer> ids) {
        return jdbcTemplate.query(
            """
                SELECT * FROM "post" WHERE id in :ids
                """,
            new MapSqlParameterSource("ids", ids),
            getPostRowMapper()
        );
    }

    @Override
    public @NotNull List<Post> loadAllByUserId(int ownerId) {
        return jdbcTemplate.query(
            """
                SELECT * FROM "post" WHERE owner_id = :owner_id
                """,
            new MapSqlParameterSource("owner_id", ownerId),
            getPostRowMapper()
        );
    }

    @Override
    public @NotNull List<Post> getAllAfterDateTime(@NotNull List<Integer> userIds, @NotNull LocalDateTime since) {
        return jdbcTemplate.query(
            """
                SELECT * FROM "post" WHERE owner_id in :userIds
                AND create_date_time > :since
                ORDER BY create_date_time DESC
                """,
            new MapSqlParameterSource("since", Timestamp.valueOf(since)),
            getPostRowMapper()
        );
    }

    private RowMapper<Post> getPostRowMapper() {
        return (resultSet, rowNum) -> new Post(
            resultSet.getInt("id"),
            resultSet.getInt("owner_id"),
            resultSet.getInt("content_id"),
            resultSet.getString("description"),
            resultSet.getTimestamp("create_date_time").toLocalDateTime(),
            resultSet.getBoolean("is_deleted")
        );
    }
}
