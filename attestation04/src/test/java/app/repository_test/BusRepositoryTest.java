package app.repository_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.models.Bus;
import bus_app.models.Driver;
import bus_app.models.Path;
import bus_app.repositories.*;
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
public class BusRepositoryTest extends TestWithContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusRepository repository;

    @Test
    @DisplayName("Buses - Repository - Read test")
    public void busReadTest() {
        List<Bus> buses = repository.findAll();

        assertThat(buses, hasSize(3));
        assertThat(buses.stream().map(Bus::getNumber).toList(),
                containsInAnyOrder("31247586903214567890", "54837291045628374651",
                        "69012384572301985624"));
        assertThat(buses.stream().map(Bus::getPath).map(Path::getDuration).toList(),
                containsInAnyOrder(2400000, 3600000, 5400000));
    }

    @Test
    @DisplayName("Buses - Repository - Update test")
    public void busUpdateTest() {
        Bus testBus = repository.getReferenceById("31247586903214567890");

        testBus.setType("электрический");
        repository.saveAndFlush(testBus);

        Bus result = entityManager.find(Bus.class, "31247586903214567890");

        assertThat(result.getNumber(), equalTo("31247586903214567890"));
        assertThat(result.getPath().getNumber(), equalTo("с799"));
        assertThat(result.getDepartment().getId(), equalTo(1L));
        assertThat(result.getDrivers().stream().map(Driver::getId).toList(),
                containsInAnyOrder(5L, 6L));
        assertThat(result.getSeatsNumber(), equalTo(30));
        assertThat(result.getType(), equalTo("электрический"));
        assertThat(result.getIsActive(), equalTo(true));
    }

    @Test
    @DisplayName("Buses - Repository - Delete test")
    public void busDeleteTest() {
        repository.deleteById("31247586903214567890");

        Bus result = entityManager.find(Bus.class, "31247586903214567890");
        assertThat(result, notNullValue());
        assertThat(result.getIsDeleted(), equalTo(true));
    }

    @Test
    @DisplayName("Buses - Repository - Find by id test")
    public void findByIdTest() {
        Optional<Bus> testBus1 = repository.findById("31247586903214567890");
        assertThat(testBus1.isPresent(), equalTo(true));

        Bus result = entityManager.find(Bus.class, "31247586903214567890");
        result.setIsDeleted(true);
        entityManager.flush();

        Optional<Bus> testBus2 = repository.findById("31247586903214567890");
        assertThat(testBus2.isPresent(), equalTo(false));
    }

    @Test
    @DisplayName("Buses - Repository - Return to list test")
    public void returnToListTest() {
        repository.returnToListById("98765432100123456789");

        Bus result = entityManager.find(Bus.class, "98765432100123456789");
        assertThat(result, notNullValue());
    }
}