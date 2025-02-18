package org.delonce.homework13;

import org.delonce.homework13.controller.StudentController;
import org.delonce.homework13.model.Student;
import org.delonce.homework13.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = StudentController.class)
@ContextConfiguration(classes = StudentController.class)
public class StudentControllerTests {
    @Autowired
    private StudentController studentController;
    @MockitoBean
    private StudentService studentService;
    @Autowired
    private MockMvc mvc;

    @Test
    public void getStudent() throws Exception {
        Student student = new Student(1L, "Павел", "pavel@gmail.com", "Курс1");

        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistentStudent() throws Exception {
        Mockito.when(studentService.getStudentById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createStudent() throws Exception {
        Student request = new Student();
        request.setEmail("example@gmail.com");

        when(studentService.createStudent(request)).thenReturn(request);

        mvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createStudentNonValidEmail() throws Exception {
        Student request = new Student();
        request.setEmail("examplegmailcom");

        mvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
