package app.repositories_test;

import app.exceptions.NoDataException;
import app.repositories_test.abstracts.TestDBContainerInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GradeRepositoryIncorrectTest extends TestDBContainerInitializer {

    public GradeRepositoryIncorrectTest() {
        super();
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Grades: incorrect read test")
    public void testRead() {
        try {
            repository.readGrade(9999);
            assert false;
        } catch (NoDataException ignored) {}
    }
}