package app.controllers;

import app.services.CommentService;
import app.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping()
    public Comment postNewComment(@RequestParam("student") Integer studentId,
                                  @RequestParam("grade") Integer gradeId,
                                  @RequestBody String text) {

        return service.addComment(new Comment(
                null, studentId, gradeId, text
        ));
    }

    @GetMapping("/student/{id}")
    public List<Comment> getAllCommentsByStudent(@PathVariable("id") Integer id) {
        return service.getAllCommentsFromStudent(id);
    }

    @GetMapping("/grade/{id}")
    public List<Comment> getAllCommentsForGrade(@PathVariable("id") Integer id) {
        return service.getAllCommentsForGrade(id);
    }
}