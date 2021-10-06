package ua.knu.mishagram.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(
        @Value("${ua.knu.mishagram.database.url}") String jdbcUrl,
        @Value("${ua.knu.mishagram.database.username}") String username,
        @Value("${ua.knu.mishagram.database.password}") String password,
        @Value("${ua.knu.mishagram.database.driver-class-name}") String driverClassName
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setPoolName("mishagram-application-connection-pool");
        hikariConfig.setMaximumPoolSize(100);
        hikariConfig.setConnectionTimeout(5000);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate h2JdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public MigrateResult migrateDatabase(
        DataSource dataSource,
        @Value("classpath:db/migration") String location
    ) {
        return Flyway.configure()
            .locations(location)
            .dataSource(dataSource)
            .load()
            .migrate();
    }

}
