package ua.knu.mishagram.user;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.User;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    Optional<User> getById(int id) {
        return jdbcTemplate.queryForStream(
            "SELECT * from \"user\" where id = :id",
            Map.of("id", id),
            getUserRowMapper()
        ).findAny();
    }

    Optional<User> getByEmail(String email) {
        return jdbcTemplate.queryForStream(
            "SELECT * from \"user\" where email = :email",
            Map.of("email", email),
            getUserRowMapper()
        ).findAny();
    }

    List<User> getByIds(List<Integer> ids) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ids", ids);
        if(ids.isEmpty()) {
            return Collections.emptyList();
        }
        return jdbcTemplate.query(
            "SELECT * from \"user\" where \"user\".id in (:ids)",
            sqlParameterSource,
            getUserRowMapper()
        );
    }

    void save(User user) {
        Map<String, Object> parameters = Map.of(
            "email", user.getEmail(),
            "registered_date_time", Timestamp.valueOf(user.getRegisteredDateTime()),
            "is_deleted", user.isDeleted(),
            "password_hash", user.getPasswordHash()
        );
        jdbcTemplate.update(
            """
                INSERT INTO "user" (email, registered_date_time, is_deleted, password_hash)
                values (:email, :registered_date_time, :is_deleted, :password_hash)
                """,
            parameters
        );
    }

    void update(User user) {
        Map<String, Object> parameters = Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "registered_date_time", Timestamp.valueOf(user.getRegisteredDateTime()),
            "is_deleted", user.isDeleted(),
            "password_hash", user.getPasswordHash()
        );
        jdbcTemplate.update(
            """
                UPDATE "user" SET
                email = :email,
                registered_date_time = :registered_date_time,
                is_deleted = :is_deleted,
                password_hash = :password_hash
                WHERE id = :id
                """,
            parameters
        );
    }

    boolean userExists(int userId) {
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT * FROM \"user\" WHERE id = :id)",
                Map.of("id", userId),
                Boolean.class
            )
        );

    }

    List<User> loadAll() {
        return jdbcTemplate.getJdbcTemplate().query(
            "SELECT * from \"user\"",  getUserRowMapper()
        );
    }

    private RowMapper<User> getUserRowMapper() {
        return (resultSet, rowNum) -> new User(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getBoolean("is_deleted"),
            resultSet.getTimestamp("registered_date_time").toLocalDateTime(),
            resultSet.getString("password_hash")
        );
    }
}
