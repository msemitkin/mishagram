package ua.knu.mishagram.post.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.test.util.TestUtils;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
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

    @InjectMocks
    private GetPostService getPostService;

    @Test
    void getById_shouldThrowExceptionWhen_postNotExists() {
        when(loadPostPort.loadById(5)).thenReturn(Optional.empty());

        Assertions.assertThrows(PostNotFoundException.class, () -> getPostService.getById(5));
    }

    @Test
    void getById_successFlow() {
        Post post = new Post(5, 55, "text", TEST_DATE, false);
        when(loadPostPort.loadById(5))
            .thenReturn(Optional.of(post));

        Post actualPost = getPostService.getById(5);

        TestUtils.assertJsonModelsEquals(post, actualPost);
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

        List<Post> actualPosts = getPostService.getAllByOwnerId(USER_ID);

        Assertions.assertTrue(actualPosts.isEmpty());
    }

    @Test
    void getByOwnerId_shouldReturnEmptyList_successFlow() {
        List<Post> posts = List.of(
            new Post(1, USER_ID, "descr1", TEST_DATE, false),
            new Post(2, USER_ID, "descr2", TEST_DATE, false)
        );
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);
        when(loadPostPort.loadAllByUserId(USER_ID)).thenReturn(posts);

        List<Post> actualPosts = getPostService.getAllByOwnerId(USER_ID);

        TestUtils.assertJsonModelsEquals(posts, actualPosts);
    }
}