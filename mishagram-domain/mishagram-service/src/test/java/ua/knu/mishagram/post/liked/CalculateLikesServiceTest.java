package ua.knu.mishagram.post.liked;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.get.PostExistsPort;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateLikesServiceTest {

    private static final int POST_ID = 555;

    @Mock
    private PostExistsPort postExistsPort;
    @Mock
    private CalculateLikesPort calculateLikesPort;

    @InjectMocks
    private CalculateLikesService calculateLikesService;

    @Test
    void calculateLikes_shouldThrowException_whenPostNotExists() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(false);

        Assertions.assertThrows(PostNotFoundException.class, () -> calculateLikesService.calculateLikes(POST_ID));
    }

    @Test
    void calculateLikes_successFlow() {
        when(postExistsPort.postExists(POST_ID)).thenReturn(true);
        when(calculateLikesPort.calculateLikes(POST_ID)).thenReturn(55);

        int actualLikes = calculateLikesService.calculateLikes(POST_ID);

        int expectedLikes = 55;
        Assertions.assertEquals(expectedLikes, actualLikes);
    }
}