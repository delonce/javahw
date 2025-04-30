package bus_app.repositories;

import bus_app.models.Station;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Класс - репозиторий для доступа к записям об остановках
 */
@Repository
@CacheConfig(cacheNames = "stations")
public interface StationRepository extends JpaRepository<Station, Long> {

    @Caching(
            put = @CachePut(key = "#station.id"),
            evict = {@CacheEvict(key = "'all'"),
                @CacheEvict(value = "paths", allEntries = true),
                @CacheEvict(value = "buses", allEntries = true)}
    )
    Station saveAndFlush(Station station);

    @Override
    @Cacheable(key = "'all'")
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Station> findAll();

    @Override
    @Cacheable(key = "#id")
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.id = ?1")
    Optional<Station> findById(Long id);

    @Transactional
    @Override
    @Modifying
    @Caching(
            evict = {@CacheEvict(key = "#id"),
                @CacheEvict(key = "'all'"),
                @CacheEvict(value = "paths", allEntries = true),
                @CacheEvict(value = "buses", allEntries = true)}
    )
    @Query("update #{#entityName} b set b.isDeleted = true where b.id = ?1")
    void deleteById(Long id);
}