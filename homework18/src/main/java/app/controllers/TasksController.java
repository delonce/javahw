package app.controllers;

import app.models.Task;
import app.models.dto.TaskAddRequestDto;
import app.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс web - контроллера, предоставляющий интерфейс для работы со списком заданий
 * @author Danma
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TasksController {

    /** Поле Jpa - репозитория для доступа к записям зажаний в базе данных */
    private final TaskRepository repository;

    /**
     * Процедура чтения из базы данных всех заданий
     * @return список всех заданий
     */
    @GetMapping
    public List<Task> readTasks() {
        return repository.findAll();
    }

    /**
     * Процедура добавления в базу данных новой записи с информацией о задании
     * @param task информация о добавляемом задании (название, описание)
     * @return новая запись из базы данных
     */
    @PostMapping
    public Task addTask(@RequestBody TaskAddRequestDto task) {
        return repository.saveAndFlush(new Task(
                null, task.getName(), task.getDescription(), LocalDateTime.now()
        ));
    }

    /**
     * Процедура удаления записи о задании из базы данных
     * @param id идентификатор записи о задании в базе данных
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}