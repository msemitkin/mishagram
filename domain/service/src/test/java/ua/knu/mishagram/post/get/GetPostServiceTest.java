package ua.knu.mishagram.post.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.content.LoadFileContentPort;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.test.util.TestUtils;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPostServiceTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(10, 10, 10, 10, 10);
    private static final int USER_ID = 555;
    @Mock
    private UserExistsPort userExistsPort;
    @Mock
    private LoadPostPort loadPostPort;
    @Mock
    private LoadFileContentPort loadFileContentPort;

    @InjectMocks
    private GetPostService getPostService;

    @Test
    void getById_shouldThrowExceptionWhen_postNotExists() {
        when(loadPostPort.loadById(5)).thenReturn(Optional.empty());

        Assertions.assertThrows(PostNotFoundException.class, () -> getPostService.getById(5));
    }

    @Test
    void getById_successFlow() {
        Post post = new Post(5, 55, 555, "text", TEST_DATE, false);
        when(loadPostPort.loadById(5))
            .thenReturn(Optional.of(post));
        when(loadFileContentPort.loadById(555))
            .thenReturn(Optional.of(new Content(555, "name", "ext", new byte[100])));

        PostComposite actualPost = getPostService.getById(5);

        PostComposite expectedPostComposite = new PostComposite(5, 55, new Content(555, "name", "ext", new byte[100]), "text", TEST_DATE, false);
        TestUtils.assertJsonModelsEquals(expectedPostComposite, actualPost);
    }

    @Test
    void getByOwnerId_shouldThrowException_whenPostNotExists() {
        Assertions.assertThrows(PostNotFoundException.class, () -> getPostService.getById(5));
    }

    @Test
    void getByOwnerId_shouldThrowException_whenUserNotExists() {
        when(userExistsPort.userExists(USER_ID)).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class, () -> getPostService.getAllByOwnerId(USER_ID));
    }

    @Test
    void getByOwnerId_shouldReturnEmptyList_whenUserDoesNotHavePosts() {
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);

        List<PostComposite> actualPosts = getPostService.getAllByOwnerId(USER_ID);

        Assertions.assertTrue(actualPosts.isEmpty());
    }

    @Test
    void getByOwnerId_shouldReturnEmptyList_successFlow() {
        List<Post> posts = List.of(
            new Post(1, USER_ID, 11, "descr1", TEST_DATE, false),
            new Post(2, USER_ID, 22, "descr2", TEST_DATE, false)
        );
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);
        when(loadPostPort.loadAllByUserId(USER_ID)).thenReturn(posts);
        when(loadFileContentPort.loadAllByIds(List.of(11, 22)))
            .thenReturn(Map.of(
                11, new Content(11, "name11", "ext11", new byte[11]),
                22, new Content(22, "name22", "ext22", new byte[22])
            ));

        List<PostComposite> actualPosts = getPostService.getAllByOwnerId(USER_ID);

        List<PostComposite> expectedPosts = List.of(
            new PostComposite(1, USER_ID, new Content(11, "name11", "ext11", new byte[11]), "descr1", TEST_DATE, false),
            new PostComposite(2, USER_ID, new Content(22, "name22", "ext22", new byte[22]), "descr2", TEST_DATE, false)
        );
        TestUtils.assertJsonModelsEquals(expectedPosts, actualPosts);
    }
}