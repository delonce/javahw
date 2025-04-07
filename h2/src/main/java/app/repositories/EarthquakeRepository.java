package app.repositories;

import app.models.entities.EarthquakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthquakeRepository extends JpaRepository<EarthquakeEntity, Long> {

    List<EarthquakeEntity> findByMagAfter(Double mag);

    List<EarthquakeEntity> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}