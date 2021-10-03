package ua.knu.mishagram.post.create;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Content;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.SavePostContentPort;
import ua.knu.mishagram.post.SavePostPort;
import ua.knu.mishagram.time.DateTimeProvider;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;

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
    private UserExistsPort userExistsPort;
    @Mock
    private DateTimeProvider dateTimeProvider;
    @Mock
    private SavePostContentPort savePostContentPort;

    @InjectMocks
    private CreatePostService createPostService;

    @Test
    void createPost_shouldThrowException_whenUserNotExists() {
        CreatePostCommand createPostCommand =
            new CreatePostCommand(USER_ID, "some text", new Content(0, "name", "ext", new byte[11]));
        when(userExistsPort.userExists(USER_ID)).thenReturn(false);

        Assertions.assertThrows(
            UserNotFoundException.class,
            () -> createPostService.createPost(createPostCommand)
        );
    }

    @Test
    void createPost_successFlow() {
        Content content = new Content(0, "name", "ext", new byte[11]);
        int contentId = 15;
        CreatePostCommand createPostCommand =
            new CreatePostCommand(USER_ID, "some text", content);
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);
        when(dateTimeProvider.now()).thenReturn(DATE_TIME);
        when(savePostContentPort.savePostContent(content)).thenReturn(contentId);

        createPostService.createPost(createPostCommand);

        Post expectedPost = new Post(0, USER_ID, contentId, "some text", DATE_TIME, false);
        verify(savePostPort).save(argThat(actualPost -> Objects.equals(toJson(actualPost), toJson(expectedPost))));
    }
}