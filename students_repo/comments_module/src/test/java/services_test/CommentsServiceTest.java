package services_test;

import app.entities.Comment;
import app.repositories.CommentRepository;
import app.services.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class CommentsServiceTest {

    protected final CommentRepository repository;
    protected final CommentService service;

    public CommentsServiceTest() {
        repository = Mockito.mock(CommentRepository.class);
        service = new CommentService(repository);
    }

    @Test
    @DisplayName("Service: get comments by student test")
    public void getCommentsByStudentTest() {
        Comment comment = new Comment(1, 1, 1, "some_text");
        List<Comment> comments = List.of(comment);

        Mockito.when(repository.findByStudentId(1)).thenReturn(comments);

        assert service.getAllCommentsFromStudent(1).equals(comments);
    }

    @Test
    @DisplayName("Service: get comments by grade test")
    public void getCommentsByGradeTest() {
        Comment comment = new Comment(1, 1, 1, "some_text");
        List<Comment> comments = List.of(comment);

        Mockito.when(repository.findByGradeId(1)).thenReturn(comments);

        assert service.getAllCommentsForGrade(1).equals(comments);
    }

    @Test
    @DisplayName("Service: add comment test")
    public void addCommentTest() {
        Comment comment = new Comment(1, 1, 1, "some_text");

        Mockito.when(repository.saveAndFlush(comment)).thenReturn(comment);

        assert service.addComment(comment).equals(comment);
    }
}
