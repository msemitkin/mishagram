package ua.knu.mishagram.post.get;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;

import java.util.Map;

@Repository
public class PostExistsAdapter extends JdbcRepository implements PostExistsPort {

    public PostExistsAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public boolean postExists(int postId) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT * FROM post WHERE id = :id)",
                Map.of("id", postId),
                Boolean.class
            )
        );
    }
}
