package ua.knu.mishagram.post.delete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.exceptions.PostNotFoundException;
import ua.knu.mishagram.post.UpdatePostPort;
import ua.knu.mishagram.post.get.LoadPostPort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletePostServiceTest {

    private static final int POST_ID = 777;
    private static final int OWNER_ID = 888;
    private static final int CONTENT_ID = 999;
    private static final LocalDateTime CREATE_DATE_TIME = LocalDateTime.of(1010, 10, 10, 10, 10);

    @Mock
    private LoadPostPort loadPostPort;
    @Mock
    private UpdatePostPort updatePostPort;

    @InjectMocks
    private DeletePostService deletePostService;

    @Test
    void deletePostById_shouldThrowException_whenPostDoesNotExist() {
        when(loadPostPort.loadById(POST_ID))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(
            PostNotFoundException.class,
            () -> deletePostService.deletePostById(POST_ID)
        );

    }

    @Test
    void deletePostById_successFlow() {
        when(loadPostPort.loadById(POST_ID))
            .thenReturn(Optional.of(new Post(POST_ID, OWNER_ID, CONTENT_ID, "some text", CREATE_DATE_TIME, false)));

        deletePostService.deletePostById(POST_ID);

        verify(updatePostPort).update(refEq(new Post(POST_ID, OWNER_ID, CONTENT_ID, "some text", CREATE_DATE_TIME, true)));
    }
}