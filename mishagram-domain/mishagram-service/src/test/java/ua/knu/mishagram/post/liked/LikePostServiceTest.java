package ua.knu.mishagram.post.liked;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.exceptions.UserNotFoundException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikePostServiceTest {

    private static final int POST_ID = 555;
    private static final int USER_ID = 666;
    @Mock
    private PostExistsPort postExistsPort;
    @Mock
    private UserExistsPort userExistsPort;
    @Mock
    private LikePostPort likePostPort;

    @InjectMocks
    private LikePostService likePostService;

    @Test
    void likePost_shouldThrowPostNotFoundException_whenPostNotExists() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(false);

        Assertions.assertThrows(PostNotFoundException.class, () -> likePostService.likePost(POST_ID, USER_ID));
    }
    
    @Test
    void likePost_shouldThrowUserNotFoundException_whenUserNotExists() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);
        when(userExistsPort.userExists(USER_ID)).thenReturn(false);
        
        Assertions.assertThrows(UserNotFoundException.class, () -> likePostService.likePost(POST_ID, USER_ID));
    }

    @Test
    void likePost_successFlow() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);

        likePostService.likePost(POST_ID, USER_ID);

        verify(likePostPort).likePost(POST_ID, USER_ID);
    }
}