package ua.knu.mishagram.errorhandling.accessdenied;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.DelegatingAccessDeniedHandler;
import ua.knu.mishagram.security.accesscheckers.DataAccessDeniedException;

import java.util.LinkedHashMap;

@Configuration
public class AccessDeniedHandlersConfiguration {

    @Bean
    public DelegatingAccessDeniedHandler delegatingAccessDeniedHandler(
        DataAccessDeniedExceptionHandler dataAccessDeniedExceptionHandler,
        DefaultAccessDeniedExceptionHandler defaultAccessDeniedExceptionHandler
    ) {
        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers =
            new LinkedHashMap<>();
        handlers.put(DataAccessDeniedException.class, dataAccessDeniedExceptionHandler);
        return new DelegatingAccessDeniedHandler(handlers, defaultAccessDeniedExceptionHandler);
    }
}
