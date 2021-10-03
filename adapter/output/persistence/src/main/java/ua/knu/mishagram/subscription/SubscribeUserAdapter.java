package ua.knu.mishagram.subscription;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;

import java.util.Map;

@Repository
public class SubscribeUserAdapter extends JdbcRepository implements SubscribeUserPost {

    public SubscribeUserAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void subscribe(int userId, int targetUserId) {
        jdbcTemplate.update(
            "INSERT INTO subscription(user_id, target_user_id)VALUES (:user_id, :target_user_id)",
            Map.of("user_id", userId, "target_user_id", targetUserId)
        );
    }
}
