package ua.knu.mishagram.content;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.JdbcRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class LoadFileContentPortAdapter extends JdbcRepository implements LoadFileContentPort {


    public LoadFileContentPortAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Content> loadById(int id) {
        return jdbcTemplate.queryForStream(
            """
                SELECT * FROM content WHERE id = :id
                """,
            Map.of("id", id),
            CONTENT_ROW_MAPPER
        ).findAny();

    }

    @Override
    public Map<Integer, Content> loadAllByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyMap();
        }
        return jdbcTemplate.queryForStream(
                "SELECT * FROM content WHERE id in (:ids)",
                new MapSqlParameterSource("ids", ids),
                CONTENT_ROW_MAPPER
            )
            .collect(Collectors.toMap(Content::getId, Function.identity()));
    }

    private static final RowMapper<Content> CONTENT_ROW_MAPPER =
        (resultSet, rowNum) -> new Content(
            resultSet.getInt("id"),
            resultSet.getString("file_name"),
            resultSet.getString("file_type"),
            resultSet.getBytes("data")
        );
}
