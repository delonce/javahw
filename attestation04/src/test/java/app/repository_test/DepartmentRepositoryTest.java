package app.repository_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.models.Department;
import bus_app.repositories.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class DepartmentRepositoryTest extends TestWithContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository repository;

    @Test
    @DisplayName("Departments - Repository - Read test")
    public void departmentReadTest() {
        List<Department> departments = repository.findAll();

        assertThat(departments, hasSize(2));
        assertThat(departments.stream().map(Department::getName).toList(),
                containsInAnyOrder("Департамент №1", "Департамент №2"));
    }

    @Test
    @DisplayName("Departments - Repository - Update test")
    public void departmentUpdateTest() {
        Department departmentTest = repository.getReferenceById(1L);

        departmentTest.setName("тестовое имя");
        repository.saveAndFlush(departmentTest);

        Department result = entityManager.find(Department.class, 1L);

        assertThat(result.getName(), equalTo("тестовое имя"));
        assertThat(result.getAddress(), equalTo("Москва, ул. Садовая, 1"));
    }

    @Test
    @DisplayName("Departments - Repository - Delete test")
    public void departmentDeleteTest() {
        repository.deleteById(1L);

        Department result = entityManager.find(Department.class, 1L);
        assertThat(result, notNullValue());
        assertThat(result.getIsDeleted(), equalTo(true));
    }

    @Test
    @DisplayName("Departments - Repository - Find by id test")
    public void departmentByIdTest() {
        Optional<Department> testDepartment1 = repository.findById(1L);
        assertThat(testDepartment1.isPresent(), equalTo(true));

        Department result = entityManager.find(Department.class, 1L);
        result.setIsDeleted(true);
        entityManager.flush();

        Optional<Department> testDepartment2 = repository.findById(1L);
        assertThat(testDepartment2.isPresent(), equalTo(false));
    }
}