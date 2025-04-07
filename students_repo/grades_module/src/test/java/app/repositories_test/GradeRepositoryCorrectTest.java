package app.repositories_test;

import app.exceptions.NoDataException;
import app.models.Grade;
import app.repositories_test.abstracts.TestDBContainerInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GradeRepositoryCorrectTest extends TestDBContainerInitializer {

    public GradeRepositoryCorrectTest() {
        super();
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Grades: correct read test")
    public void testRead() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into grades values(default, 'test_name', '2025-02-02', true) returning id",
                Integer.class
        );

        Grade grade = repository.readGrade(id);

        assert grade.getIsActive().equals(true);
        assert grade.getName().equals("test_name");
        assert grade.getStartDate().toString().equals("2025-02-02");
    }

    @Test
    @DisplayName("Grades: correct make active test")
    public void testMakeActive() {
        Integer id = template.queryForObject(
                "insert into grades values(default, 'test_name', '2025-02-02', false) returning id",
                Integer.class
        );

        Grade grade = repository.makeGradeActive(id);

        assert grade.getIsActive().equals(true);
    }

    @Test
    @DisplayName("Grades: correct make non active test")
    public void testMakeNonActive() {
        Integer id = template.queryForObject(
                "insert into grades values(default, 'test_name', '2025-02-02', true) returning id",
                Integer.class
        );

        Grade grade = repository.makeGradeNonActive(id);

        assert grade.getIsActive().equals(false);
    }
}
