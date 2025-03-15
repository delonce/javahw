package app.controllers;

import app.configs.SessionData;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoAuthorizationException;
import app.exceptions.NoDataException;
import app.models.Comment;
import app.models.Student;
import app.services.StudentService;
import app.specifications.StudentSpecification;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;
    private final SessionData sessionData;

    @PostMapping
    public Student createStudent(@RequestBody Student student) throws IncorrectBodyException, NoDataException {
        Student newStudent = service.create(student);
        sessionData.setSessionId(newStudent.getId());

        return newStudent;
    }

    @PostMapping("/comments")
    public Comment postComment(@RequestParam("student") Integer studentId,
                               @RequestParam("grade") Integer gradeId,
                               @RequestBody String text) throws NoDataException, NoAuthorizationException {

        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(studentId)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.addComment(studentId, gradeId, text);
    }

    @GetMapping("/comments/student")
    public List<Comment> getCommentsByStudent(@RequestParam("id") Integer id) throws NoAuthorizationException {
        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(id)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.getCommentsByStudent(id);
    }

    @GetMapping("/comments/grade")
    public List<Comment> getCommentsByGrade(@RequestParam("id") Integer id) throws NoDataException {
        return service.getCommentsByGrade(id);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Integer id) throws NoDataException {
        Student newStudent = service.read(id);
        sessionData.setSessionId(newStudent.getId());

        return newStudent;
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam("grade_id") Integer id) throws NoDataException {
        return service.getAllStudents(id);
    }

    @GetMapping("/age")
    public List<Student> getStudentsByAge(@RequestParam("age") Integer age, @RequestParam("relation") String relation) {
        return service.getAllByAge(age, relation);
    }

    @GetMapping("/count")
    public Integer getStudentsCount() {
        return service.getStudentsCount();
    }

    @GetMapping("/name/sorted")
    public List<Student> getStudentsSortedByFullName() {
        return service.getStudentsSortedByFullName();
    }

    @GetMapping("/email/longest")
    public Student getStudentWithLongestEmail() throws NoDataException {
        return service.getStudentWithLongestEmail();
    }

    @GetMapping("/grades/more")
    public List<Student> getStudentsWithGradesCountMoreThan(@RequestParam("count") Integer count) {
        return service.getStudentsWithGradesCountMoreThan(count);
    }

    @GetMapping("/search")
    public List<Student> getStudentsByFilter(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String email
    ) {
        Specification<Student> spec = StudentSpecification.createSpec(
                fullName, age, email);

        return service.getStudentsByFilter(spec);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Integer id, @RequestBody Student student)
            throws IncorrectBodyException, NoDataException, NoAuthorizationException {
        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(id)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.update(id, student);
    }

    @PutMapping("/{id}/{grade_id}")
    public Student addGrade(@PathVariable("id") Integer id, @PathVariable("grade_id") Integer gradeId)
            throws NoDataException, NoAuthorizationException {
        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(id)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.addGrade(id, gradeId);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, HttpSession session) throws NoDataException {
        service.delete(id);

        session.invalidate();

        return "Record has been deleted!";
    }

    @DeleteMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
