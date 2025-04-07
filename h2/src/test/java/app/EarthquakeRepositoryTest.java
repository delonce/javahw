package app;

import app.models.entities.EarthquakeEntity;
import app.repositories.EarthquakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class EarthquakeRepositoryTest {

    private final LocalDateTime date1 = LocalDateTime.of(
            2025, 1, 20, 12, 33, 1);
    private final LocalDateTime date2 = LocalDateTime.of(
            2020, 6, 3, 21, 4, 17);

    @Autowired
    private EarthquakeRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();

        repository.save(new EarthquakeEntity(1L, "title1", date1, 1.1, "place1"));
        repository.save(new EarthquakeEntity(2L, "title2", date2, 2.8, "place2"));
    }

    @Test
    @DisplayName("Repository: mag test")
    public void magTest() {
        List<String> entities = repository.findByMagAfter(2.0)
                .stream().map(EarthquakeEntity::getTitle).toList();

        assert entities.size() == 1;
        assert entities.contains("title2");
    }

    @Test
    @DisplayName("Repository: time test")
    public void timeTest() {
        List<String> entities = repository.findByTimeBetween(
                LocalDateTime.of(2021, 1, 1, 1, 1),
                LocalDateTime.of(2026, 1, 1, 1, 1)
        ).stream().map(EarthquakeEntity::getTitle).toList();

        assert entities.size() == 1;
        assert entities.contains("title1");
    }
}