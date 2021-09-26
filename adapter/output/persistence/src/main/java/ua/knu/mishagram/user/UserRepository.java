package ua.knu.mishagram.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ua.knu.mishagram.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    User getById(int id) {
        return jdbcTemplate.queryForObject(
            "SELECT * from \"user\" where id = :id",
            Map.of("id", id),
            getUserRowMapper()
        );
    }

    User getByEmail(String email) {
        return jdbcTemplate.queryForObject(
            "SELECT * from \"user\" where email = :email",
            Map.of("email", email),
            getUserRowMapper()
        );
    }

    List<User> getByIds(List<Integer> ids) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ids", ids);
        return jdbcTemplate.query(
            "SELECT * from \"user\" where id in :ids",
            sqlParameterSource,
            getUserRowMapper()
        );
    }

    void save(User user) {
        Map<String, Object> parameters = Map.of(
            "email", user.getEmail(),
            "registered_date_time", Timestamp.valueOf(user.getRegisteredDateTime()),
            "is_deleted", user.isDeleted()
        );
        jdbcTemplate.update(
                """
                INSERT INTO "user" (email, registered_date_time, is_deleted)
                values (:email, :registered_date_time, :is_deleted)
                """,
            parameters
        );
    }

    void update(User user) {
        Map<String, Object> parameters = Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "registered_date_time", Timestamp.valueOf(user.getRegisteredDateTime()),
            "is_deleted", user.isDeleted()
        );
        jdbcTemplate.update(
            """
                UPDATE "user" SET
                email = :email,
                registered_date_time = :registered_date_time,
                is_deleted = :is_deleted
                WHERE id = :id
                """,
            parameters
        );
    }

    private RowMapper<User> getUserRowMapper() {
        return (resultSet, rowNum) -> new User(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getBoolean("is_deleted"),
            resultSet.getTimestamp("registered_date_time").toLocalDateTime()
        );
    }
}
