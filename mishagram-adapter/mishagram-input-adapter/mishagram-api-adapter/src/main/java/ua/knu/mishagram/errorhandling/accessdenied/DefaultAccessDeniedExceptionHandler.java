package ua.knu.mishagram.errorhandling.accessdenied;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ua.knu.mishagram.errorhandling.ErrorDto;
import ua.knu.mishagram.errorhandling.ErrorReason;

@Component
public class DefaultAccessDeniedExceptionHandler extends CommonAccessDeniedHandler {

    DefaultAccessDeniedExceptionHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    @NotNull
    protected HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    @NotNull
    protected ErrorDto getResponseBody() {
        return new ErrorDto(
            ErrorReason.ACCESS_DENIED,
            "Access denied"
        );
    }
}
