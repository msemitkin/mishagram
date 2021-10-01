package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;
import ua.knu.mishagram.Post;

import java.sql.Timestamp;
import java.util.Map;

@Repository
public class SavePostAdapter extends JdbcRepository implements SavePostPort, UpdatePostPort {

    public SavePostAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void save(@NotNull Post post) {
        Map<String, Object> parameters = Map.of(
            "description", post.getDescription(),
            "owner_id", post.getOwnerId(),
            "create_date_time", Timestamp.valueOf(post.getCreateDateTime()),
            "is_deleted", post.isDeleted()
        );
        jdbcTemplate.update(
            """
                INSERT INTO post (description, owner_id, create_date_time, is_deleted)
                values (:description, :owner_id, :create_date_time, :is_deleted)
                """,
            parameters
        );
    }


    @Override
    public void update(@NotNull Post post) {
        Map<String, Object> parameters = Map.of(
            "id", post.getId(),
            "description", post.getDescription(),
            "owner_id", post.getOwnerId(),
            "create_date_time", Timestamp.valueOf(post.getCreateDateTime()),
            "is_deleted", post.isDeleted()
        );
        jdbcTemplate.update(
            """
                UPDATE post SET
                description = :description,
                owner_id = :owner_id,
                create_date_time = :create_date_time,
                is_deleted = :is_deleted
                WHERE id = :id
                """,
            parameters
        );
    }

}
