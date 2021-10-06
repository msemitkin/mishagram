package ua.knu.mishagram.content;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.DatabaseException;
import ua.knu.mishagram.JdbcRepository;
import ua.knu.mishagram.post.SavePostContentPort;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Repository
public class SavePostContentAdapter extends JdbcRepository implements SavePostContentPort {

    public SavePostContentAdapter(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public int savePostContent(Content content) {
        Map<String, Serializable> parameters = Map.of(
            "file_name", content.getFileName(),
            "file_type", content.getFileExtension(),
            "data", content.getContent()
        );
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    """
                            INSERT INTO content(file_name, file_type, data)
                            values(:file_name, :file_type, :data) RETURNING id;
                        """,
                    parameters,
                    (resultSet, rowNum) -> resultSet.getInt("id")
                )
            ).orElseThrow(() -> new DatabaseException("Failed to obtain id of inserted row"));
        } catch (EmptyResultDataAccessException e) {
            throw new DatabaseException("Failed to obtain id of inserted row");
        }
    }
}
