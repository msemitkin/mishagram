package ua.knu.mishagram.content;

import ua.knu.mishagram.Content;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoadFileContentPort {

    Optional<Content> loadById(int id);

    Map<Integer, Content> loadAllByIds(List<Integer> ids);

}
