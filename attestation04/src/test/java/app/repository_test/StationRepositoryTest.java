package app.repository_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.models.Station;
import bus_app.repositories.StationRepository;
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
public class StationRepositoryTest extends TestWithContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StationRepository repository;

    @Test
    @DisplayName("Stations - Repository - Read test")
    public void stationReadTest() {
        List<Station> stations = repository.findAll();

        assertThat(stations, hasSize(16));
        assertThat(stations.stream().map(Station::getName).sorted().limit(4).toList(),
                contains("138-й квартал Выхина", "2-я Черногрязская улица",
                        "Гидропроект", "Карамышевская набережная"));
    }

    @Test
    @DisplayName("Stations - Repository - Update test")
    public void stationUpdateTest() {
        Station stationTest = repository.getReferenceById(1L);

        stationTest.setName("тестовое имя");
        repository.saveAndFlush(stationTest);

        Station result = entityManager.find(Station.class, 1L);

        assertThat(result.getName(), equalTo("тестовое имя"));
        assertThat(result.getDistrict(), equalTo("Строгино"));
    }

    @Test
    @DisplayName("Stations - Repository - Delete test")
    public void stationDeleteTest() {
        repository.deleteById(1L);

        Station result = entityManager.find(Station.class, 1L);
        assertThat(result, notNullValue());
        assertThat(result.getIsDeleted(), equalTo(true));
    }

    @Test
    @DisplayName("Stations - Repository - Find by id test")
    public void stationByIdTest() {
        Optional<Station> testStation1 = repository.findById(1L);
        assertThat(testStation1.isPresent(), equalTo(true));

        Station result = entityManager.find(Station.class, 1L);
        result.setIsDeleted(true);
        entityManager.flush();

        Optional<Station> testStation2 = repository.findById(1L);
        assertThat(testStation2.isPresent(), equalTo(false));
    }
}