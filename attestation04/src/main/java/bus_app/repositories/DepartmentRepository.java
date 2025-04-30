package bus_app.repositories;

import bus_app.models.Department;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Класс - репозиторий для доступа к записям о департаментах
 */
@Repository
@CacheConfig(cacheNames = "departments")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Caching(
            put = @CachePut(key = "#department.id"),
            evict = {@CacheEvict(key = "'all'"),
                @CacheEvict(value = "buses", allEntries = true)}
    )
    Department saveAndFlush(Department department);

    @Override
    @Cacheable(key = "'all'")
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Department> findAll();

    @Override
    @Cacheable(key = "#id")
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.id = ?1")
    Optional<Department> findById(Long id);

    @Transactional
    @Override
    @Modifying
    @Caching(
            evict = {@CacheEvict(key = "#id"),
                @CacheEvict(key = "'all'"),
                @CacheEvict(value = "buses", allEntries = true)}
    )
    @Query("update #{#entityName} b set b.isDeleted = true where b.id = ?1")
    void deleteById(Long id);
}