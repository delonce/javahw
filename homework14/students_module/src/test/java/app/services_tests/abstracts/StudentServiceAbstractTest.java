package app.services_tests.abstracts;

import app.clients.CommentClient;
import app.clients.GradeClient;
import app.repositories.StudentJdbcRepository;
import app.repositories.StudentJpaRepository;
import app.services.StudentService;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class StudentServiceAbstractTest {

    protected final GradeClient gradeClient;
    protected final CommentClient commentClient;
    protected final StudentJdbcRepository studentJdbcRepository;
    protected final StudentJpaRepository studentJpaRepository;
    protected final StudentService service;
    protected final PasswordEncoder encoder;

    public StudentServiceAbstractTest() {
        studentJdbcRepository = Mockito.mock(StudentJdbcRepository.class);
        studentJpaRepository = Mockito.mock(StudentJpaRepository.class);
        gradeClient = Mockito.mock(GradeClient.class);
        commentClient = Mockito.mock(CommentClient.class);
        encoder = Mockito.mock(PasswordEncoder.class);
        service = new StudentService(studentJdbcRepository, studentJpaRepository, gradeClient, commentClient, encoder);
    }
}