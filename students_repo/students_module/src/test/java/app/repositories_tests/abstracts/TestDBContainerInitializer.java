package app.repositories_tests.abstracts;

import app.repositories.StudentJdbcRepository;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class TestDBContainerInitializer {

    public static PostgreSQLContainer<?> POSTGRES;
    public static Flyway flyway;
    public static DriverManagerDataSource source;

    protected final StudentJdbcRepository studentJdbcRepository;
    protected final JdbcTemplate template;

    public TestDBContainerInitializer() {
        template = new JdbcTemplate(TestDBContainerInitializer.source);
        studentJdbcRepository = new StudentJdbcRepository(template);
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
        source.setSchema("students_schema");

        flyway = Flyway.configure()
                .cleanDisabled(false)
                .dataSource(source)
                .load();
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
