package app.controllers;

import app.models.Task;
import app.models.dto.TaskAddRequestDto;
import app.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TasksController {

    private final TaskRepository repository;

    @GetMapping
    public List<Task> readTasks() {
        return repository.findAll();
    }

    @PostMapping
    public Task addTask(@RequestBody TaskAddRequestDto task) {
        return repository.saveAndFlush(new Task(
                null, task.getName(), task.getDescription(), LocalDateTime.now()
        ));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}