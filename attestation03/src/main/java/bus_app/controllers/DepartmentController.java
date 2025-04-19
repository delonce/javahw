package bus_app.controllers;

import bus_app.dto.departments.DepartmentDto;
import bus_app.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Департаменты", description = "API для взаимодействия с записями о департаментах")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(
            summary = "Получить список всех департаментов",
            description = "Возвращает список всех доступных департаментов",
            tags = {"Департаменты"}
    )
    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.readAllDepartments().stream()
                .map(DepartmentDto::new).toList();
    }

    @Operation(
            summary = "Добавить в базу данных новый департамент",
            description = "Добавляет запись о новом департаменте в базу данных и возвращает информацию о нем",
            tags = {"Департаменты"}
    )
    @PostMapping
    public DepartmentDto addDepartment(
            @Parameter(description = "Информация о департаменте")
            @RequestBody DepartmentDto department) {
        return new DepartmentDto(departmentService.addDepartment(department));
    }

    @Operation(
            summary = "Изменить информацию о департаменте",
            description = "Изменяет информацию о департаменте на предоставленную и возвращает измененную запись",
            tags = {"Департаменты"}
    )
    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(
            @Parameter(description = "Идентификатор департамента")
            @PathVariable("id") Long id,
            @Parameter(description = "Информация о департаменте")
            @RequestBody DepartmentDto department) {
        return new DepartmentDto(departmentService.updateDepartment(id, department));
    }

    @Operation(
            summary = "Удалить запись о департаменте",
            description = "Удаляет запись о департаменте с предоставленным id",
            tags = {"Департаменты"}
    )
    @DeleteMapping("/{id}")
    public void deleteDepartment(
            @Parameter(description = "Идентификатор департамента")
            @PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
    }
}