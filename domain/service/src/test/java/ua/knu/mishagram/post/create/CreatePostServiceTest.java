package ua.knu.mishagram.post.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.User;
import ua.knu.mishagram.post.SavePostPort;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.LoadUserPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.knu.mishagram.test.util.TestUtils.toJson;

@ExtendWith(MockitoExtension.class)
class CreatePostServiceTest {

    private static final int USER_ID = 5;
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2020, 10, 10, 10, 10);

    @Mock
    private SavePostPort savePostPort;
    @Mock
    private LoadUserPort loadUserPort;
    @Mock
    private DateTimeProvider dateTimeProvider;

    @InjectMocks
    private CreatePostService createPostService;

    @Test
    void createPost_shouldThrowException_whenUserNotExists() {
        CreatePostCommand createPostCommand = new CreatePostCommand(USER_ID, "some text");
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(
            UserNotFoundException.class,
            () -> createPostService.createPost(createPostCommand)
        );
    }

    @Test
    void createPost_successFlow() {
        CreatePostCommand createPostCommand = new CreatePostCommand(USER_ID, "some text");
        User user = new User(USER_ID, "some email", false, LocalDateTime.parse("1010-10-10T10:10"));
        when(loadUserPort.loadById(USER_ID)).thenReturn(Optional.of(user));
        when(dateTimeProvider.now()).thenReturn(DATE_TIME);

        createPostService.createPost(createPostCommand);

        Post expectedPost = new Post(0, USER_ID, "some text", DATE_TIME, false);
        verify(savePostPort).save(argThat(actualPost -> Objects.equals(toJson(actualPost), toJson(expectedPost))));
    }
}