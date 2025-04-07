package app.controllers_test;

import app.controllers_test.abstracts.GradeControllerAbstractTest;
import app.models.Grade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

public class GradeControllerCorrectTest extends GradeControllerAbstractTest {

    @Test
    @DisplayName("Grades: read request test")
    public void testRead() throws Exception {
        LocalDate date = LocalDate.now();
        Grade grade = new Grade(1, "test_name", date, true);

        Mockito.when(service.readGrade(1)).thenReturn(grade);

        mockMvc.perform(MockMvcRequestBuilders.get("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(date.toString()));
    }

    @Test
    @DisplayName("Grades: make active test")
    public void testMakeActive() throws Exception {
        Grade grade = new Grade(1, "test_name", LocalDate.now(), true);

        Mockito.when(service.makeGradeActive(1)).thenReturn(grade);

        mockMvc.perform(MockMvcRequestBuilders.put("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive").value("true"));
    }

    @Test
    @DisplayName("Grades: make non active test")
    public void testMakeNonActive() throws Exception {
        Grade grade = new Grade(1, "test_name", LocalDate.now(), false);

        Mockito.when(service.makeGradeNonActive(1)).thenReturn(grade);

        mockMvc.perform(MockMvcRequestBuilders.delete("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isActive").value("false"));
    }
}
