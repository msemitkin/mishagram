package ua.knu.mishagram.errorhandling.accessdenied;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import ua.knu.mishagram.errorhandling.ErrorDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class CommonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    protected CommonAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(getHttpStatus().value());
        objectMapper.writeValue(response.getOutputStream(), getResponseBody());
    }

    @NotNull
    protected abstract HttpStatus getHttpStatus();

    @NotNull
    protected abstract ErrorDto getResponseBody();

}
