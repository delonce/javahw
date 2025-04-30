package app;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

public abstract class TestWithContainer {

    protected static PostgreSQLContainer<?> POSTGRES;

    protected static DataSource DATA_SOURCE;

    protected static Flyway FLYWAY;

    static {
        POSTGRES = new PostgreSQLContainer<>(
                "postgres:latest"
        ).withDatabaseName("bus_db")
                .withUsername("postgres")
                .withPassword("postgres");

        POSTGRES.start();

        DATA_SOURCE = new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        );

        FLYWAY = Flyway.configure()
                .cleanDisabled(false)
                .locations("classpath:db/migration")
                .dataSource(DATA_SOURCE)
                .load();

        FLYWAY.clean();
        FLYWAY.migrate();
    }

    @DynamicPropertySource
    protected static void configureDataBaseSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}