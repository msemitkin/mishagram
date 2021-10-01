package ua.knu.mishagram.time;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeProvider {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
