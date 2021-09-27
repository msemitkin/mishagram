package ua.knu.mishagram.post.favourite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;
import ua.knu.mishagram.user.UserNotFoundException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemovePostFromFavouritesServiceTest {

    private static final int USER_ID = 777;
    private static final int POST_ID = 888;

    @Mock
    private RemovePostFromFavouritesPort removePostFromFavouritesPort;
    @Mock
    private UserExistsPort userExistsPort;
    @Mock
    private PostExistsPort postExistsPort;

    @InjectMocks
    private RemovePostFromFavouritesService removePostFromFavouritesService;

    @Test
    void removeFromFavourites_shouldThrowUserNotFoundException_whenUserNotExists() {
        when(userExistsPort.userExists(USER_ID)).thenReturn(false);

        UserNotFoundException exception = Assertions.assertThrows(
            UserNotFoundException.class,
            () -> removePostFromFavouritesService.removeFromFavourites(POST_ID, USER_ID)
        );
        Assertions.assertEquals(USER_ID, exception.getId());
    }

    @Test
    void removeFromFavourites_shouldThrowPostNotFoundException_whenPostNotExists() {
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);
        when(postExistsPort.postExists(POST_ID)).thenReturn(false);

        PostNotFoundException exception = Assertions.assertThrows(
            PostNotFoundException.class,
            () -> removePostFromFavouritesService.removeFromFavourites(POST_ID, USER_ID)
        );
        Assertions.assertEquals(POST_ID, exception.getId());
    }

    @Test
    void removeFromFavourites_successFlow() {
        when(userExistsPort.userExists(USER_ID)).thenReturn(true);
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);

        removePostFromFavouritesService.removeFromFavourites(POST_ID, USER_ID);

        verify(removePostFromFavouritesPort).removeFromFavourites(POST_ID, USER_ID);
    }
}