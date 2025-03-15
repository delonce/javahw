package app.controllers_tests;

import app.controllers_tests.abstracts.StudentControllerAbstractTest;
import app.models.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class StudentControllerIncorrectTest extends StudentControllerAbstractTest {

    @Test
    @DisplayName("Students: update request UnAuthorized test")
    public void updateIncorrectTest() throws Exception {
        Student requestStudent = new Student(
                null, null, null, "new_test_email", null
        );

        Mockito.when(sessionData.getSessionId()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestStudent))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("No authorization!"));
    }

    @Test
    @DisplayName("Students: add grade request test")
    public void addGradeTest() throws Exception {
        Mockito.when(sessionData.getSessionId()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1/2"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("No authorization!"));
    }
}
