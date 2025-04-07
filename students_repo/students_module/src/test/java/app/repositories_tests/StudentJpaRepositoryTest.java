package app.repositories_tests;

import app.models.Student;
import app.repositories.StudentJpaRepository;
import app.repositories_tests.abstracts.TestDBContainerInitializer;
import app.specifications.StudentSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class StudentJpaRepositoryTest extends TestDBContainerInitializer {

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Test
    @DisplayName("Students: jpa age more test")
    public void findStudentsWithMoreThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithMoreThanAge(30).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 2;
        assert ages.contains(37) && ages.contains(42);
    }

    @Test
    @DisplayName("Students: jpa age less test")
    public void findStudentsWithLessThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithLessThanAge(30).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 2;
        assert ages.contains(28) && ages.contains(19);
    }

    @Test
    @DisplayName("Students: jpa age equal test")
    public void findStudentsWithEqualThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithAge(28).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 1;
        assert ages.contains(28);
    }

    @Test
    @DisplayName("Students: jpa name test")
    public void findByOrderByFullNameTest() {
        List<String> names = studentJpaRepository.findByOrderByFullName()
                .stream().map(Student::getFullName).toList();

        List<String> list = List.of("Козлов Александр Савельевич",
                "Львов Константин Павлович",
                "Наумова Анна Артёмовна",
                "Овчинникова Вероника Максимовна");

        assert names.equals(list);
    }

    @Test
    @DisplayName("Students: jpa email test")
    public void findTop1ByEmailTest() {
        List<String> emails = studentJpaRepository.findTop1ByEmail()
                .stream().map(Student::getEmail).toList();

        assert emails.get(0).equals("vewad_ewicu45@hotmail.com");
    }

    @Test
    @DisplayName("Students: grades count test")
    public void gradesCountTest() {
        List<Student> students = new ArrayList<>(List.of(
                new Student(
                        2, "Наумова Анна Артёмовна", 42, "vewad_ewicu45@hotmail.com",
                        new Integer[] {1, 4}
                ),
                new Student(
                        4, "Козлов Александр Савельевич", 28, "yomano_wavo78@gmail.com",
                        new Integer[] {1, 3}
                )
        ));

        assert studentJpaRepository.findWithMoreGradesThan(1).equals(students);
    }

    @Test
    @DisplayName("Students: filter test")
    public void filterTest() {
        Specification<Student> spec = StudentSpecification
                .createSpec("Константин", 37, "lotizaj");
        List<Student> studentsWithName = studentJpaRepository.findAll(spec);
        System.out.println();

        assert studentsWithName.size() == 1;
        assert studentsWithName.get(0).getFullName().equals("Львов Константин Павлович");
    }
}
