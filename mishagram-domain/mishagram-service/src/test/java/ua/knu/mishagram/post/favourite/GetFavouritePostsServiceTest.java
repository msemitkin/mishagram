package ua.knu.mishagram.post.favourite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.knu.mishagram.Post;
import ua.knu.mishagram.post.get.LoadPostPort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFavouritePostsServiceTest {

    private static final int USER_ID = 777;
    private static final LocalDateTime TEST_DATE = LocalDateTime.of(1010, 10, 10, 10, 10);

    @Mock
    private LoadFavouritePostsPort loadFavouritePostsPort;
    @Mock
    private LoadPostPort loadPostPort;

    @InjectMocks
    private GetFavouritePostsService getFavouritePostsService;

    @Test
    void getUserFavouritePosts_returnsEmptyList_whenNoPostsWereFoundForGivenUser() {
        when(loadFavouritePostsPort.getFavouritePostsByUserId(USER_ID))
            .thenReturn(Collections.emptyList());

        List<Post> favouritePosts = getFavouritePostsService.getUserFavouritePosts(USER_ID);

        Assertions.assertTrue(favouritePosts.isEmpty());
    }

    @Test
    void getUserFavouritePosts_successFlow() {
        List<Integer> postIds = List.of(444, 666);
        List<Post> posts = List.of(
            new Post(444, USER_ID, 11, "text444", TEST_DATE, false),
            new Post(666, USER_ID, 22, "text666", TEST_DATE, false)
        );
        when(loadFavouritePostsPort.getFavouritePostsByUserId(USER_ID))
            .thenReturn(postIds);
        when(loadPostPort.loadAllByIds(postIds)).thenReturn(posts);

        List<Post> actualPosts = getFavouritePostsService.getUserFavouritePosts(USER_ID);

        assertThat(actualPosts)
            .usingRecursiveComparison()
            .isEqualTo(posts);
    }
}