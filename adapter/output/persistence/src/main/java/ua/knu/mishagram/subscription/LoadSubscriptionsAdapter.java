package ua.knu.mishagram.subscription;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.JdbcRepository;

import java.util.List;
import java.util.Map;

@Repository
public class LoadSubscriptionsAdapter extends JdbcRepository implements LoadUserSubscriptionsPort {

    public LoadSubscriptionsAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<Integer> getAllByUserId(int userId) {
        return jdbcTemplate.queryForList(
            "SELECT target_user_id FROM subscription WHERE user_id = :userId",
            Map.of("userId", userId),
            Integer.class
        );
    }
}
