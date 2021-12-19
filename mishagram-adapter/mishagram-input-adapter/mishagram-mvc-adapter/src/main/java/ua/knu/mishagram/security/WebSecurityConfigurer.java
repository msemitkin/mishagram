package ua.knu.mishagram.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.knu.mishagram.OauthProvider;
import ua.knu.mishagram.user.get.GetUserUseCase;
import ua.knu.mishagram.user.register.RegisterOauthUserCommand;
import ua.knu.mishagram.user.register.RegisterUserUseCase;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfigurer(
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .mvcMatchers("/error", "/webjars/**", "/login")
                .permitAll()
                .mvcMatchers("/users/form")
                .hasRole("ANONYMOUS")
                .mvcMatchers(HttpMethod.POST, "/users")
                .hasRole("ANONYMOUS")
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
            .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(
        GetUserUseCase getUserUseCase,
        RegisterUserUseCase registerUserUseCase
    ) {
        return map -> {
            String oauthId  = (String) map.get("sub");
            return getUserUseCase.getByOauthId(oauthId)
                .orElseGet(() -> {
                    String email = (String) map.get("email");
                    RegisterOauthUserCommand registerUserCommand = new RegisterOauthUserCommand(
                        oauthId,
                        OauthProvider.GOOGLE,
                        email
                    );
                    registerUserUseCase.registerOauthUser(registerUserCommand);
                    return getUserUseCase.getByOauthId(oauthId)
                        .orElseThrow(() -> new RuntimeException("Failed to save user"));
                });
        };
    }
}
