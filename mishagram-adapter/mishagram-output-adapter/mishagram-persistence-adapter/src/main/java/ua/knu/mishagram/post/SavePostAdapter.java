package ua.knu.mishagram.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void save(@NotNull Post post) {
        Map<String, Object> parameters = Map.of(
            "description", post.getDescription(),
            "owner_id", post.getOwnerId(),
            "create_date_time", Timestamp.valueOf(post.getCreateDateTime()),
            "is_deleted", post.isDeleted(),
            "content_id", post.getContentId()
        );
        int postId = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate()).withTableName("post")
            .usingGeneratedKeyColumns("id")
            .executeAndReturnKey(parameters)
            .intValue();
        if (post.getCoordinates() != null) {
            new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate()).withTableName("post_coordinates")
                .usingGeneratedKeyColumns("id")
                .execute(
                    Map.of(
                        "post_id", postId,
                        "longitude", post.getCoordinates().getLongitude(),
                        "latitude", post.getCoordinates().getLatitude()
                    )
                );
        }
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
