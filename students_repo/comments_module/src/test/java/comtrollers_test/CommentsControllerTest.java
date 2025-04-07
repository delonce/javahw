package comtrollers_test;

import app.CommentsApplication;
import app.controllers.CommentController;
import app.entities.Comment;
import app.services.CommentService;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@ContextConfiguration(classes={CommentsApplication.class})
public class CommentsControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    protected CommentService service;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Mock for GradeController configuration test")
    public void givenWac_whenServletContext_thenItProvidesStudentController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertInstanceOf(MockServletContext.class, servletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("commentController"));
    }

    @Test
    @DisplayName("Controller: post comment test")
    public void postCommentTest() throws Exception {
        Comment comment = new Comment(null, 1, 1, "some_text");

        Mockito.when(service.addComment(comment)).thenReturn(comment);

        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                .param("student", "1")
                .param("grade", "1")
                .content("some_text"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("some_text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gradeId").value("1"));
    }

    @Test
    @DisplayName("Controller: get comments by student test")
    public void getCommentsByStudentTest() throws Exception {
        Comment comment = new Comment(null, 1, 1, "some_text");
        List<Comment> comments = List.of(comment);

        Mockito.when(service.getAllCommentsFromStudent(1)).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/student/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("some_text"));
    }

    @Test
    @DisplayName("Controller: get comments by grade test")
    public void getCommentsByGradeTest() throws Exception {
        Comment comment = new Comment(null, 1, 1, "some_text");
        List<Comment> comments = List.of(comment);

        Mockito.when(service.getAllCommentsForGrade(1)).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/comments/grade/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("some_text"));
    }
}
