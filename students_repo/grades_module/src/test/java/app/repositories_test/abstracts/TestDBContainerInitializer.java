package app.repositories_test.abstracts;

import app.repositories.GradeRepository;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public class TestDBContainerInitializer {

    public static PostgreSQLContainer<?> POSTGRES;
    public static Flyway flyway;
    public static DataSource source;

    protected final GradeRepository repository;
    protected final JdbcTemplate template;

    public TestDBContainerInitializer() {
        template = new JdbcTemplate(TestDBContainerInitializer.source);
        repository = new GradeRepository(template);
    }

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("mvc_test_db")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES.start();

        source = new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        );

        flyway = Flyway.configure()
                .cleanDisabled(false)
                .dataSource(source)
                .load();
    }
}
