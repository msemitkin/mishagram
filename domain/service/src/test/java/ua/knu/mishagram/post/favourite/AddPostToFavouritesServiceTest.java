package ua.knu.mishagram.post.favourite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ua.knu.mishagram.exceptions.UserNotFoundException;
import ua.knu.mishagram.post.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;
import ua.knu.mishagram.user.UserExistsPort;

import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

    @ParameterizedTest
    @MethodSource("addPostToFavouritesParameters")
    void addPostToFavourites_parametrizedTest(int userId, int postId, boolean userExists, boolean postExists, Class<? extends Throwable> expectedException) {
        when(existsPort.userExists(userId)).thenReturn(userExists);
        when(postExistsPort.postExists(postId)).thenReturn(postExists);

        Assertions.assertThrows(
            expectedException,
            () -> addPostToFavouritesService.addPostToFavourites(POST_ID, USER_ID)
        );
    }

    static Stream<Arguments> addPostToFavouritesParameters() {
        return Stream.of(
            Arguments.of(USER_ID, POST_ID, true, false, PostNotFoundException.class),
            Arguments.of(USER_ID, POST_ID, false, true, UserNotFoundException.class)
        );
    }

}
