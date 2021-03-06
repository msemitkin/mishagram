package ua.knu.mishagram.post.favourite;

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
class AddPostToFavouritesServiceTest {

    private static final int USER_ID = 777;
    private static final int POST_ID = 888;

    @Mock
    private UserExistsPort existsPort;
    @Mock
    private PostExistsPort postExistsPort;
    @Mock
    private AddPostToFavouritesPort addPostToFavouritesPort;

    @InjectMocks
    private AddPostToFavouritesService addPostToFavouritesService;

    @Test
    void addPostToFavourites_shouldThrowUserNotFoundException_whenUserNotExists() {
        when(existsPort.userExists(USER_ID)).thenReturn(false);

        Assertions.assertThrows(
            UserNotFoundException.class,
            () -> addPostToFavouritesService.addPostToFavourites(POST_ID, USER_ID)
        );
    }

    @Test
    void addPostToFavourites_shouldPostNotFoundException_whenPostNotExists() {
        when(existsPort.userExists(USER_ID)).thenReturn(true);
        when(postExistsPort.postExists(POST_ID)).thenReturn(false);

        Assertions.assertThrows(
            PostNotFoundException.class,
            () -> addPostToFavouritesService.addPostToFavourites(POST_ID, USER_ID)
        );
    }

    @Test
    void addPostToFavourites_successFlow() {
        when(existsPort.userExists(USER_ID)).thenReturn(true);
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);

        addPostToFavouritesService.addPostToFavourites(POST_ID, USER_ID);

        verify(addPostToFavouritesPort).add(POST_ID, USER_ID);
    }

}
