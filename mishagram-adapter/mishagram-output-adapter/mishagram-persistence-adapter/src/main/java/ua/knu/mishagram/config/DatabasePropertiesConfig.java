package ua.knu.mishagram.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("default")
@PropertySource("classpath:secrets.properties")
public class DatabasePropertiesConfig {
}
