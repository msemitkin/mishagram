package ua.knu.mishagram.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.knu.mishagram.User;
import ua.knu.mishagram.user.get.GetUserUseCase;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetUserUseCase getUserUseCase;

    public UserDetailsServiceImpl(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserUseCase.getByEmail(username);
        return toUserDetails(user);
    }

    private UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}
