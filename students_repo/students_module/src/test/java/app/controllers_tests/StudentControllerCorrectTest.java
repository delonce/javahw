package app.controllers_tests;

import app.controllers_tests.abstracts.StudentControllerAbstractTest;
import app.models.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

public class StudentControllerCorrectTest extends StudentControllerAbstractTest {

    @Test
    @DisplayName("Students: create request test")
    public void createTest() throws Exception {
        Student student = new Student(
                1, "test_user", 19, "test_email", new Integer[] {1}
        );

        Mockito.when(service.create(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(student))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));;
    }

    @Test
    @DisplayName("Students: get request test")
    public void getTest() throws Exception {
        Student student = new Student(
                1, "test_user", 19, "test_email", new Integer[] {1}
        );

        Mockito.when(service.read(1)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));;
    }

    @Test
    @DisplayName("Students: update request test")
    public void updateTest() throws Exception {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", new Integer[] {1}
        );

        Student requestStudent = new Student(
                null, null, null, "new_test_email", null
        );

        Mockito.when(sessionData.getSessionId()).thenReturn(1);
        Mockito.when(service.update(1, requestStudent)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(requestStudent))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new_test_email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("test_user"));
    }

    @Test
    @DisplayName("Students: add grade request test")
    public void addGradeTest() throws Exception {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", new Integer[] {1, 2}
        );

        Mockito.when(sessionData.getSessionId()).thenReturn(1);
        Mockito.when(service.addGrade(1, 2)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gradesList[0]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gradesList[1]").value("2"));
    }

    @Test
    @DisplayName("Students: delete request test")
    public void deleteTest() throws Exception {
        Mockito.when(service.delete(1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Record has been deleted!"));
    }

    @Test
    @DisplayName("Students: get all by grade test")
    public void getAllByGradeTest() throws Exception {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", new Integer[] {1, 2}
        );
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(service.getAllStudents(1)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .param("grade_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value("test_user"));
    }
}
