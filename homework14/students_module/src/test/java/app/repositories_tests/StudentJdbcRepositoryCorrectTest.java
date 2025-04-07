package app.repositories_tests;

import app.repositories_tests.abstracts.TestDBContainerInitializer;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.mappers.StudentMapper;
import app.models.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StudentJdbcRepositoryCorrectTest extends TestDBContainerInitializer{

    public StudentJdbcRepositoryCorrectTest() {
        super();
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Students: correct create test")
    public void testCreate() throws IncorrectBodyException, NoDataException {
        Student student = studentJdbcRepository.createStudent(new Student(
                null, "testName testSecondName testThirdName", 19, "test_email@test.ru", "test_password", new String[] {"USER"},new Integer[] {1, 2, 3}
        ));

        Student receivedStudent = template.queryForObject(
                "select * from students where id = ?",
                new StudentMapper(),
                student.getId()
        );

        assert (student.getEmail().equals(receivedStudent.getEmail()));
        assert (student.getAge()).equals(receivedStudent.getAge());
        assert (student.getFullName().equals(receivedStudent.getFullName()));
        assert (Arrays.equals(student.getGradesList(), receivedStudent.getGradesList()));
    }

    @Test
    @DisplayName("Students: correct read test")
    public void testRead() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 19, 'test_email_3', 'test_password', ARRAY['USER'], ARRAY[1]) returning id",
                Integer.class
        );

        Student student = studentJdbcRepository.readStudent(id);
        assert (student.getFullName().equals("test_user"));
        assert (student.getAge()).equals(19);
        assert (student.getEmail().equals("test_email_3"));
        assert (Arrays.stream(student.getGradesList()).collect(Collectors.toSet()).contains(1));
    }

    @Test
    @DisplayName("Students: correct update test")
    public void updateTest() throws IncorrectBodyException, NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 19, 'test_email_4', 'test_password', ARRAY['USER'], ARRAY[1]) returning id",
                Integer.class
        );

        Student student = studentJdbcRepository.updateStudent(id,
                new Student(null, "newTestName testSecondName testThirdName", null, null, null, null, null));

        assert (student.getFullName().equals("newTestName testSecondName testThirdName"));
    }

    @Test
    @DisplayName("Students: correct add grade test")
    public void addGradeTest() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 19, 'test_email', 'test_password', ARRAY['USER'], ARRAY[1]) returning id",
                Integer.class
        );

        Student student = studentJdbcRepository.addGrade(id, 2);

        assert (Arrays.stream(student.getGradesList()).collect(Collectors.toSet()).contains(2));
    }

    @Test
    @DisplayName("Students: correct delete test")
    public void deleteTest() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 19, 'test_email', 'test_password', ARRAY['USER'], ARRAY[1]) returning id",
                Integer.class
        );

        studentJdbcRepository.deleteStudent(id);

        try {
            template.queryForObject(
                    "select * from students where id = ?",
                    new StudentMapper(),
                    id
            );

            assert false;
        } catch (EmptyResultDataAccessException ignored) {}
    }

    @Test
    @DisplayName("Students: correct get all students test")
    public void getAllTest() {
        template.execute("delete from students");
        template.execute("insert into students values(default, 'test_user_1', 19, 'test_email_1', 'test_password', ARRAY['USER'], ARRAY[1])");
        template.execute("insert into students values(default, 'test_user_2', 19, 'test_email_2', 'test_password', ARRAY['USER'], ARRAY[1])");

        List<String> students = studentJdbcRepository.getAllStudentsByGrade(1).stream()
                .map(Student::getFullName)
                .toList();

        assert students.size() == 2;
        assert students.contains("test_user_1");
        assert students.contains("test_user_2");
    }
}
