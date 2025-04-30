package app.repository_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.models.Path;
import bus_app.models.PathStation;
import bus_app.models.Station;
import bus_app.repositories.PathRepository;
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

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PathRepositoryTest extends TestWithContainer {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PathRepository repository;

    @Test
    @DisplayName("Paths - Repository - Read test")
    public void pathReadTest() {
        List<Path> paths = repository.findAll();

        assertThat(paths, hasSize(3));
        assertThat(paths.stream().map(Path::getNumber).toList(),
                containsInAnyOrder("м3", "265",
                        "с799"));
        assertThat(paths.stream().map(Path::getBeginStation).map(Station::getName).toList(),
                containsInAnyOrder("Серебряный бор", "Метро Бульвар Рокоссовского",
                        "138-й квартал Выхина"));
    }

    @Test
    @DisplayName("Paths - Repository - Update test")
    public void pathUpdateTest() {
        Path testPath = repository.getReferenceById("м3");

        testPath.setDuration(7373291);
        repository.saveAndFlush(testPath);

        Path result = entityManager.find(Path.class, "м3");

        assertThat(result.getNumber(), equalTo("м3"));
        assertThat(result.getDuration(), equalTo(7373291));
        assertThat(result.getBeginStation().getId(), equalTo(1L));
        assertThat(result.getEndStation().getId(), equalTo(2L));
        assertThat(result.getStations().stream().map(PathStation::getStation)
                .map(Station::getId).toList(),
                containsInAnyOrder(1L, 2L, 3L, 4L));
    }

    @Test
    @DisplayName("Paths - Repository - Delete test")
    public void pathDeleteTest() {
        repository.deleteById("м3");

        Path result = entityManager.find(Path.class, "м3");
        assertThat(result, notNullValue());
        assertThat(result.getIsDeleted(), equalTo(true));
    }

    @Test
    @DisplayName("Paths - Repository - Find by id test")
    public void findByIdTest() {
        Optional<Path> testPath1 = repository.findById("м3");
        assertThat(testPath1.isPresent(), equalTo(true));

        Path result = entityManager.find(Path.class, "м3");
        result.setIsDeleted(true);
        entityManager.flush();

        Optional<Path> testPath2 = repository.findById("м3");
        assertThat(testPath2.isPresent(), equalTo(false));
    }

    @Test
    @DisplayName("Paths - Repository - Return to list test")
    public void returnToListTest() {
        repository.returnToListById("403");

        Path result = entityManager.find(Path.class, "403");
        assertThat(result, notNullValue());
    }
}