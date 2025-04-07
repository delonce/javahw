package app.services;

import app.aspects.LogExecTime;
import app.clients.CommentClient;
import app.clients.GradeClient;
import app.exceptions.GradeIsNotActiveException;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Comment;
import app.models.Grade;
import app.models.Student;
import app.models.dto.StudentForUserDTO;
import app.repositories.StudentJdbcRepository;
import app.repositories.StudentJpaRepository;
import app.security.StudentDetails;
import app.services.interfaces.BasedCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements BasedCRUDService<Student> {

    private final StudentJdbcRepository studentJdbcRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final GradeClient gradeClient;
    private final CommentClient commentClient;

    private final PasswordEncoder encoder;

    public Student getStudentByEmail(String email) throws NoDataException {
        return studentJpaRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NoDataException("Incorrect email: " + email));
    }

    @Override
    @LogExecTime
    public Student create(Student obj) throws IncorrectBodyException, NoDataException {
        Student.isStudentCorrect(obj);
        for (Integer gradeId: obj.getGradesList()) {
            checkGradeId(gradeId);
        }

        obj.setPassword(encoder.encode(obj.getPassword()));

        boolean isLoginAlreadyExists = studentJpaRepository.findAll().stream()
                .map(Student::getEmail)
                .anyMatch(email -> email.equals(obj.getEmail()));

        if (isLoginAlreadyExists) {
            throw new IncorrectBodyException("Such email are already used!");
        }

        return studentJdbcRepository.createStudent(obj);
    }

    @Override
    public Student read(Integer id) throws NoDataException {
        return studentJdbcRepository.readStudent(id);
    }

    @Override
    public Student update(Integer id, Student obj) throws IncorrectBodyException, NoDataException {
        if (obj.getGradesList() != null) {
            for (Integer gradeId: obj.getGradesList()) {
                checkGradeId(gradeId);
            }
        }

        Student.checkValidation(obj);

        try {
            studentJdbcRepository.readStudent(id);
        } catch (NoDataException ex) {
            return studentJdbcRepository.createStudent(obj);
        }

        return studentJdbcRepository.updateStudent(id, obj);
    }

    @Override
    public boolean delete(Integer id) throws NoDataException {
        studentJdbcRepository.readStudent(id);

        return studentJdbcRepository.deleteStudent(id);
    }

    @LogExecTime
    public Student addGrade(Integer id, Integer gradeId) throws NoDataException {
        checkGradeId(gradeId);

        return studentJdbcRepository.addGrade(id, gradeId);
    }

    @LogExecTime
    public List<Student> getAllStudents(Integer id) throws NoDataException {
        return studentJdbcRepository.getAllStudentsByGrade(id);
    }

    @LogExecTime
    public List<Student> getAllByAge(Integer age, String relation) throws IncorrectBodyException {
        if (age < 18) {
            throw new IncorrectBodyException("Incorrect received body");
        }

        switch (relation) {
            case "more" -> {
                return studentJpaRepository.findStudentsWithMoreThanAge(age);
            }
            case "less" -> {
                return studentJpaRepository.findStudentsWithLessThanAge(age);
            }
            case "equal" -> {
                return studentJpaRepository.findStudentsWithAge(age);
            }
            default -> throw new IncorrectBodyException("Incorrect received body");
        }
    }

    public List<Student> getStudentsSortedByFullName() {
        return studentJpaRepository.findByOrderByFullName();
    }

    public Integer getStudentsCount() {
        return Math.toIntExact(studentJpaRepository.count());
    }

    @LogExecTime
    public Student getStudentWithLongestEmail() throws NoDataException {
        if (getStudentsCount().equals(0)) {
            throw new NoDataException("No records in table Students");
        }

        return studentJpaRepository.findTop1ByEmail().get(0);
    }

    public List<Student> getStudentsWithGradesCountMoreThan(Integer count) {
        return studentJpaRepository.findWithMoreGradesThan(count);
    }

    public List<Student> getStudentsByFilter(Specification<Student> spec) {
        return studentJpaRepository.findAll(spec);
    }

    public Comment addComment(Integer student, Integer grade, String text) throws NoDataException {
        return commentClient.addComment(student, grade, text);
    }

    public List<Comment> getCommentsByStudent(Integer id) {
        return commentClient.getCommentsByStudent(id);
    }

    public List<Comment> getCommentsByGrade(Integer id) throws NoDataException {
        return commentClient.getCommentsByGrade(id);
    }

    private void checkGradeId(Integer id) throws NoDataException {
        if (!gradeClient.readGrade(id).getIsActive()) {
            throw new GradeIsNotActiveException("Grade with id = " + id + " is not active!");
        }
    }

    public StudentForUserDTO transformToDTO(Student student) {
        List<Grade> grades = new ArrayList<>();
        for (Integer gradeId: student.getGradesList()) {
            grades.add(gradeClient.readGrade(gradeId));
        }
        return new StudentForUserDTO(student, grades);
    }
}