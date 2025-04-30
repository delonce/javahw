package app.repository_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.models.Driver;
import bus_app.repositories.DriverRepository;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class DriverRepositoryTest extends TestWithContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DriverRepository repository;

    @Test
    @DisplayName("Drivers - Repository - Read test")
    public void driverReadTest() {
        List<Driver> drivers = repository.findAll();

        assertThat(drivers, hasSize(8));
        assertThat(drivers.stream().map(Driver::getName).toList(),
                containsInAnyOrder("Никитников Ринат", "Хусид Глеб",
                        "Зарубин Тимофей", "Гилев Юрий", "Волков Артем", "Белов Анатолий",
                        "Сухарев Игорь", "Николаевский Павел"));
    }

    @Test
    @DisplayName("Drivers - Repository - Update test")
    public void driverUpdateTest() {
        Driver driverTest = repository.getReferenceById(1L);

        driverTest.setName("тестовое имя");
        repository.saveAndFlush(driverTest);

        Driver result = entityManager.find(Driver.class, 1L);

        assertThat(result.getName(), equalTo("тестовое имя"));
        assertThat(result.getAge(), equalTo(41));
        assertThat(result.getPhone(), equalTo("+7 (915) 264-85-93"));
    }

    @Test
    @DisplayName("Drivers - Repository - Delete test")
    public void driverDeleteTest() {
        repository.deleteById(1L);

        Driver result = entityManager.find(Driver.class, 1L);
        assertThat(result, notNullValue());
        assertThat(result.getIsDeleted(), equalTo(true));
    }

    @Test
    @DisplayName("Drivers - Repository - Find by id test")
    public void driverByIdTest() {
        Optional<Driver> testDriver1 = repository.findById(1L);
        assertThat(testDriver1.isPresent(), equalTo(true));

        Driver result = entityManager.find(Driver.class, 1L);
        result.setIsDeleted(true);
        entityManager.flush();

        Optional<Driver> testDriver2 = repository.findById(1L);
        assertThat(testDriver2.isPresent(), equalTo(false));
    }
}