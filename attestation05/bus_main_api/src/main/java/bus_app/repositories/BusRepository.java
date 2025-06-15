package bus_app.repositories;

import bus_app.models.Bus;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Класс - репозиторий для доступа к записям об автобусах
 */
@Repository
@CacheConfig(cacheNames = "buses")
public interface BusRepository extends JpaRepository<Bus, String> {

    @Caching(
            put = @CachePut(key = "#bus.number"),
            evict = @CacheEvict(key = "'all'")
    )
    Bus saveAndFlush(Bus bus);

    @Override
    @Cacheable(key = "'all'")
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Bus> findAll();

    @Override
    @Cacheable(key = "#id")
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.number = ?1")
    Optional<Bus> findById(String id);

    @Transactional
    @Override
    @Modifying
    @Caching(
        evict = {@CacheEvict(key = "#id"),
            @CacheEvict(key = "'all'")}
    )
    @Query("update #{#entityName} b set b.isDeleted = true where b.number = ?1")
    void deleteById(String id);

    @Transactional
    @Modifying
    @Caching(
            put = @CachePut(key = "#id"),
            evict = @CacheEvict(key = "'all'")
    )
    @Query("update #{#entityName} b set b.isDeleted = false where b.number = ?1")
    void returnToListById(String id);
}