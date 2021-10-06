package ua.knu.mishagram.post.get;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.knu.mishagram.content.LoadFileContentPort;

import java.util.Collections;
import java.util.List;

class PostCompositeBuilderServiceTest {

    @Mock
    private LoadFileContentPort loadFileContentPort;

    private PostCompositeBuilderService postCompositeBuilderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postCompositeBuilderService = new PostCompositeBuilderService(loadFileContentPort);
    }

    @Test
    void getPostComposites_returnsEmptyList_whenGivenEmptyList() {

        List<PostComposite> postComposites = postCompositeBuilderService.getPostComposites(Collections.emptyList());

        Assertions.assertTrue(postComposites.isEmpty());
    }
}