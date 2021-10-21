package ua.knu.mishagram.post.liked;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.User;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.test.util.TestUtils;
import ua.knu.mishagram.user.LoadUserPort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUsersLikedServiceTest {

    private static final int POST_ID = 555;
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(10, 10, 10, 10, 10);
    @Mock
    private PostExistsPort postExistsPort;
    @Mock
    private LoadUsersLikedPostPort loadUsersLikedPostPort;
    @Mock
    private LoadUserPort loadUserPort;

    @InjectMocks
    private GetUsersLikedService getUsersLikedService;

    @Test
    void getUsersLiked_shouldThrowException_whenPostNotExists() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(false);

        Assertions.assertThrows(PostNotFoundException.class, () -> getUsersLikedService.getUsersLiked(POST_ID));
    }

    @Test
    void getUsersLiked_shouldReturnEmptyList_whenNoUsersLikedGivenPost() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);
        when(loadUsersLikedPostPort.getAllUsersLiked(POST_ID)).thenReturn(Collections.emptyList());

        List<User> actualUsers = getUsersLikedService.getUsersLiked(POST_ID);

        Assertions.assertTrue(actualUsers.isEmpty());
    }

    @Test
    void getUsersLiked_successFlow() {
        List<Integer> userIds = List.of(55, 66);
        List<User> users = List.of(
            new User(55, "email55", false, TEST_DATE, "some password"),
            new User(66, "email66", false, TEST_DATE, "some password")
        );
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);
        when(loadUsersLikedPostPort.getAllUsersLiked(POST_ID)).thenReturn(userIds);
        when(loadUserPort.loadAll(userIds)).thenReturn(users);

        List<User> actualUsers = getUsersLikedService.getUsersLiked(POST_ID);

        TestUtils.assertJsonModelsEquals(users, actualUsers);
    }
}