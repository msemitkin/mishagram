package ua.knu.mishagram.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;

@Configuration
public class WebApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final DelegatingAccessDeniedHandler delegatingAccessDeniedHandler;

    public WebApiSecurityConfigurer(
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder,
        DelegatingAccessDeniedHandler delegatingAccessDeniedHandler
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.delegatingAccessDeniedHandler = delegatingAccessDeniedHandler;
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
            .mvcMatchers(HttpMethod.POST, "/api/users/")
            .anonymous()
            .mvcMatchers(HttpMethod.DELETE, "/api/posts/{postId:\\d*}")
            .access("@postAccessChecker.isAllowed(#postId)")
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(delegatingAccessDeniedHandler)
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }
}
