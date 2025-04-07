package app.services_tests;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Grade;
import app.models.Student;
import app.services_tests.abstracts.StudentServiceAbstractTest;
import app.specifications.StudentSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentServiceCorrectTest extends StudentServiceAbstractTest {

    public StudentServiceCorrectTest() {
        super();
    }

    @Test
    @DisplayName("Students: service correct create")
    public void createTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", 19, "test_email@test.ru", "test_password", new String[] {"USER"}, new Integer[] {1}
        );

        Mockito.when(studentJdbcRepository.createStudent(student)).thenReturn(student);

        Grade grade = new Grade(1, "test_grade", LocalDate.now(), true);
        Mockito.when(gradeClient.readGrade(1)).thenReturn(grade);

        Student receivedStudent = service.create(student);

        assert receivedStudent.getFullName().equals(student.getFullName());
        assert receivedStudent.getAge().equals(student.getAge());
        assert receivedStudent.getEmail().equals(student.getEmail());
    }

    @Test
    @DisplayName("Students: service correct read")
    public void readTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", 19, "test_email@test.ru","test_password", new String[] {"USER"}, new Integer[] {1}
        );

        Mockito.when(studentJdbcRepository.readStudent(1)).thenReturn(student);

        Student receivedStudent = service.read(1);

        assert receivedStudent.getFullName().equals(student.getFullName());
        assert receivedStudent.getAge().equals(student.getAge());
        assert receivedStudent.getEmail().equals(student.getEmail());
    }

    @Test
    @DisplayName("Students: service correct update")
    public void updateTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", 19, "test_email@test.ru","test_password", new String[] {"USER"}, new Integer[] {1}
        );

        Mockito.when(studentJdbcRepository.updateStudent(1, student)).thenReturn(student);
        Grade grade = new Grade(1, "test_grade", LocalDate.now(), true);
        Mockito.when(gradeClient.readGrade(1)).thenReturn(grade);

        Student receivedStudent = service.update(1, student);

        assert receivedStudent.getFullName().equals(student.getFullName());
        assert receivedStudent.getAge().equals(student.getAge());
        assert receivedStudent.getEmail().equals(student.getEmail());
    }

    @Test
    @DisplayName("Students: service correct delete")
    public void deleteTest() throws NoDataException {
        Mockito.when(studentJdbcRepository.deleteStudent(1)).thenReturn(true);

        assert service.delete(1);
    }

    @Test
    @DisplayName("Students: service correct add grade")
    public void addGradeTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", 19, "test_email@test.ru","test_password", new String[] {"USER"}, new Integer[] {1, 2}
        );
        Grade grade = new Grade(2, "test_grade", LocalDate.now(), true);
        Mockito.when(gradeClient.readGrade(2)).thenReturn(grade);

        Mockito.when(studentJdbcRepository.addGrade(1, 2)).thenReturn(student);

        Student receivedStudent = service.addGrade(1, 2);

        assert Arrays.equals(receivedStudent.getGradesList(), student.getGradesList());
    }

    @Test
    @DisplayName("Students: service get all test")
    public void getAllTest() {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
        );
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentJdbcRepository.getAllStudentsByGrade(1)).thenReturn(students);

        Grade grade = new Grade(1, "test_grade", LocalDate.now(), true);
        Mockito.when(gradeClient.readGrade(1)).thenReturn(grade);

        List<Student> students1 = service.getAllStudents(1);

        assert students1.equals(students);
    }

    @Test
    @DisplayName("Students: service more age test")
    public void moreAgeTest() {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
        );
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentJpaRepository.findStudentsWithMoreThanAge(18))
                .thenReturn(students);

        List<Student> receivedList = service.getAllByAge(18, "more");
        assert receivedList.equals(students);
    }

    @Test
    @DisplayName("Students: service less age test")
    public void lessAgeTest() {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
        );
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentJpaRepository.findStudentsWithLessThanAge(20))
                .thenReturn(students);

        List<Student> receivedList = service.getAllByAge(20, "less");
        assert receivedList.equals(students);
    }

    @Test
    @DisplayName("Students: service equal age test")
    public void equalAgeTest() {
        Student student = new Student(
                1, "test_user", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
        );
        List<Student> students = new ArrayList<>(List.of(student));

        Mockito.when(studentJpaRepository.findStudentsWithAge(19))
                .thenReturn(students);

        List<Student> receivedList = service.getAllByAge(19, "equal");
        assert receivedList.equals(students);
    }

    @Test
    @DisplayName("Students: service count test")
    public void countTest() {
        Mockito.when(studentJpaRepository.count()).thenReturn(12L);

        assert service.getStudentsCount() == 12;
    }

    @Test
    @DisplayName("Students: service names sort test")
    public void namesSortTest() {
        List<Student> students = new ArrayList<>(List.of(
                new Student(
                        1, "test_user_2", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                ),
                new Student(
                        2, "test_user_1", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                )
        ));

        Mockito.when(studentJpaRepository.findByOrderByFullName()).thenReturn(students);

        assert service.getStudentsSortedByFullName().equals(students);
    }

    @Test
    @DisplayName("Students: service top email test")
    public void topEmailTest() {
        List<Student> students = new ArrayList<>(List.of(
                new Student(
                        1, "test_user_2", 19, "new_test_email_longest", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                ),
                new Student(
                        2, "test_user_1", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                )
        ));

        Mockito.when(studentJpaRepository.findTop1ByEmail()).thenReturn(students);
        Mockito.when(studentJpaRepository.count()).thenReturn(2L);

        assert service.getStudentWithLongestEmail().getEmail().equals("new_test_email_longest");
    }

    @Test
    @DisplayName("Students: service grades count test")
    public void gradesCountTest() {
        List<Student> students = new ArrayList<>(List.of(
                new Student(
                        1, "test_user_2", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[]{1, 2}
                ),
                new Student(
                        2, "test_user_1", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[]{1, 2}
                )
        ));

        Mockito.when(studentJpaRepository.findWithMoreGradesThan(1)).thenReturn(students);

        assert service.getStudentsWithGradesCountMoreThan(1).equals(students);
    }

    @Test
    @DisplayName("Students: service filter test")
    public void filterTest() {
        List<Student> students = new ArrayList<>(List.of(
                new Student(
                        1, "test_user_2", 19, "new_test_email_longest", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                ),
                new Student(
                        2, "test_user_1", 19, "new_test_email", "test_password", new String[] {"USER"},new Integer[] {1, 2}
                )
        ));

        Specification<Student> spec = StudentSpecification.hasAge(19);

        Mockito.when(studentJpaRepository.findAll(spec)).thenReturn(students);

        assert studentJpaRepository.findAll(spec).equals(students);
    }
}
