package app.services;

import app.entities.Comment;
import app.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public List<Comment> getAllCommentsForGrade(Integer id) {
        return repository.findByGradeId(id);
    }

    public List<Comment> getAllCommentsFromStudent(Integer id) {
        return repository.findByStudentId(id);
    }

    public Comment addComment(Comment comment) {
        return repository.saveAndFlush(comment);
    }
}