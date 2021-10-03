package ua.knu.mishagram.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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
                .permitAll()
            .and()
                .logout()
                .permitAll()
            .and()
                .csrf().disable();
    }
}
