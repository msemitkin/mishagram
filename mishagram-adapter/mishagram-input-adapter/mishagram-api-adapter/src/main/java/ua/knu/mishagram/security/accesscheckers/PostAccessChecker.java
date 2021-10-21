package ua.knu.mishagram.security.accesscheckers;

import org.springframework.stereotype.Component;
import ua.knu.mishagram.post.get.GetPostUseCase;
import ua.knu.mishagram.security.AuthenticationProvider;

@Component
public class PostAccessChecker {

    private final AuthenticationProvider authenticationProvider;
    private final GetPostUseCase getPostUseCase;

    public PostAccessChecker(
        AuthenticationProvider authenticationProvider,
        GetPostUseCase getPostUseCase
    ) {
        this.authenticationProvider = authenticationProvider;
        this.getPostUseCase = getPostUseCase;
    }

    public boolean isAllowed(int postId) {
        int ownerId = getPostUseCase.getById(postId).getOwnerId();
        int userId = authenticationProvider.getUser().getId();
        if (ownerId != userId) {
            throw new DataAccessDeniedException(
                String.format("User %d do not own post %d", userId, postId));
        }
        return true;
    }
}
