package ua.knu.mishagram.subscription;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;

import java.util.Map;

@Repository
public class UnsubscribeUserAdapter extends JdbcRepository implements UnsubscribeUserPort {


    public UnsubscribeUserAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void unsubscribe(int userId, int targetUserId) {
        jdbcTemplate.update(
            "DELETE FROM subscription where user_id = :user_id and target_user_id = :target_user_id",
            Map.of("user_id", userId, "target_user_id", targetUserId)
        );
    }
}
