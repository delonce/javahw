package app.controllers_test;

import app.controllers_test.abstracts.GradeControllerAbstractTest;
import app.exceptions.NoDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GradeControllerIncorrectTest extends GradeControllerAbstractTest {

    @Test
    @DisplayName("Grades: controller throws ex | read")
    public void testRead() throws Exception {
        Mockito.when(service.readGrade(1)).thenThrow(new NoDataException("No record with id = 1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("No record with id = 1"));
    }

    @Test
    @DisplayName("Grades: controller throws ex | make active")
    public void testMakeActive() throws Exception {
        Mockito.when(service.makeGradeActive(1)).thenThrow(new NoDataException("No record with id = 1"));

        mockMvc.perform(MockMvcRequestBuilders.put("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("No record with id = 1"));
    }

    @Test
    @DisplayName("Grades: controller throws ex | make non active")
    public void testMakeNonActive() throws Exception {
        Mockito.when(service.makeGradeNonActive(1)).thenThrow(new NoDataException("No record with id = 1"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/grades/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("No record with id = 1"));
    }
}
