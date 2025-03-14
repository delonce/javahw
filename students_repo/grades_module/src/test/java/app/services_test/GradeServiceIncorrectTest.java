package app.services_test;

import app.exceptions.NoDataException;
import app.services_test.abstracts.GradeServiceAbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GradeServiceIncorrectTest extends GradeServiceAbstractTest {

    @Test
    @DisplayName("Grades: service throw ex | read")
    public void testRead() throws NoDataException {
        Mockito.when(repository.readGrade(1)).thenThrow(NoDataException.class);

        try {
            service.readGrade(1);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Grades: service throw ex | make active")
    public void testMakeActive() throws NoDataException {
        Mockito.when(repository.readGrade(1)).thenThrow(NoDataException.class);

        try {
            service.makeGradeActive(1);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Grades: service throw ex | make non active")
    public void testMakeNonActive() throws NoDataException {
        Mockito.when(repository.readGrade(1)).thenThrow(NoDataException.class);

        try {
            service.makeGradeNonActive(1);
            assert false;
        } catch (NoDataException ignored) {}
    }
}
