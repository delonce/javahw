package app.controllers;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Comment;
import app.models.Student;
import app.models.dto.StudentForOtherUserDTO;
import app.models.dto.StudentForUserDTO;
import app.models.dto.StudentRegistrationDTO;
import app.services.StudentService;
import app.specifications.StudentSpecification;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    public StudentForUserDTO createStudent(@RequestBody StudentRegistrationDTO student)
            throws IncorrectBodyException {

        return new StudentForUserDTO(service.create(new Student(student)),
                new ArrayList<>());
    }

    @PostMapping("/comments")
    @PreAuthorize("hasAuthority('USER')")
    public Comment postComment(@RequestParam("student") Integer studentId,
                               @RequestParam("grade") Integer gradeId,
                               @RequestBody String text) throws NoDataException {

        return service.addComment(studentId, gradeId, text);
    }

    @GetMapping("/comments/student")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Comment> getCommentsByStudent(@RequestParam("id") Integer id) {
        return service.getCommentsByStudent(id);
    }

    @GetMapping("/comments/grade")
    public List<Comment> getCommentsByGrade(@RequestParam("id") Integer id) throws NoDataException {
        return service.getCommentsByGrade(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Student getStudent(@PathVariable("id") Integer id) throws NoDataException {

        return service.read(id);
    }

    @GetMapping("/by-email")
    @PreAuthorize("hasAuthority('USER')")
    public StudentForUserDTO getStudentByEmail(@RequestParam("email") String email) throws NoDataException {

        return service.transformToDTO(service.getStudentByEmail(email));
    }

    @GetMapping("/by-principal")
    public Map<String,String> getCurrentStudent(Principal principal){
        if(principal==null){
            return Collections.singletonMap("username","anonymous");
        }
        return Collections.singletonMap("username",principal.getName());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<StudentForOtherUserDTO> getAllStudents(@RequestParam("grade_id") Integer id) throws NoDataException {
        return service.getAllStudents(id).stream()
                .map(StudentForOtherUserDTO::new)
                .toList();
    }

    @GetMapping("/age")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Student> getStudentsByAge(@RequestParam("age") Integer age, @RequestParam("relation") String relation) {
        return service.getAllByAge(age, relation);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Integer getStudentsCount() {
        return service.getStudentsCount();
    }

    @GetMapping("/name/sorted")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Student> getStudentsSortedByFullName() {
        return service.getStudentsSortedByFullName();
    }

    @GetMapping("/email/longest")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Student getStudentWithLongestEmail() throws NoDataException {
        return service.getStudentWithLongestEmail();
    }

    @GetMapping("/grades/more")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Student> getStudentsWithGradesCountMoreThan(@RequestParam("count") Integer count) {
        return service.getStudentsWithGradesCountMoreThan(count);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Student> getStudentsByFilter(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String email
    ) {
        Specification<Student> spec = StudentSpecification.createSpec(
                fullName, age, email);

        return service.getStudentsByFilter(spec);
    }

    /*
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Student updateStudent(@PathVariable("id") Integer id, @RequestBody Student student)
            throws IncorrectBodyException, NoDataException {

        return service.update(id, student);
    }
    */

    @PutMapping("/{id}/{grade_id}")
    @PreAuthorize("hasAuthority('USER')")
    public StudentForUserDTO addGrade(@PathVariable("id") Integer id, @PathVariable("grade_id") Integer gradeId)
            throws NoDataException {

        return service.transformToDTO(service.addGrade(id, gradeId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String deleteStudent(@PathVariable("id") Integer id) throws NoDataException {
        service.delete(id);

        return "Record has been deleted!";
    }
}
