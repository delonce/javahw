package app.services_test;

import app.exceptions.NoDataException;
import app.models.Grade;
import app.services_test.abstracts.GradeServiceAbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class GradeServiceCorrectTest extends GradeServiceAbstractTest {

    @Test
    @DisplayName("Grades: service correct read")
    public void testRead() throws NoDataException {
        Grade grade = new Grade(1, "test_name", LocalDate.now(), true);

        Mockito.when(repository.readGrade(1)).thenReturn(grade);

        Grade receivedGrade = service.readGrade(1);

        assert receivedGrade.getName().equals(grade.getName());
        assert receivedGrade.getStartDate().equals(grade.getStartDate());
        assert receivedGrade.getIsActive().equals(grade.getIsActive());
    }

    @Test
    @DisplayName("Grades: service correct make active")
    public void testMakeActive() throws NoDataException {
        Grade grade = new Grade(1, "test_name", LocalDate.now(), true);

        Mockito.when(repository.makeGradeActive(1)).thenReturn(grade);

        Grade receivedGrade = service.makeGradeActive(1);
        assert receivedGrade.getIsActive().equals(true);
    }

    @Test
    @DisplayName("Grades: service correct make non active")
    public void testMakeNonActive() throws NoDataException {
        Grade grade = new Grade(1, "test_name", LocalDate.now(), false);

        Mockito.when(repository.makeGradeNonActive(1)).thenReturn(grade);

        Grade receivedGrade = service.makeGradeNonActive(1);
        assert receivedGrade.getIsActive().equals(false);
    }
}