package ua.knu.mishagram.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.get.GetUserUseCase;

@Component
public class AuthenticationProvider {

    private final GetUserUseCase getUserUseCase;

    public AuthenticationProvider(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        UserDetails userDetails = (UserDetails) principal;
        String email = userDetails.getUsername();
        return getUserUseCase.getByEmail(email);
    }

}
