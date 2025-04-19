package app.repositories;

import app.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс доступа к записям таблицы tasks
 * @author Danma
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}