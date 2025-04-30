package bus_app.controllers;

import bus_app.dto.departments.DepartmentDto;
import bus_app.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, предоставляющего API для обращения к данным департаментов
 */
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Департаменты", description = "API для взаимодействия с записями о департаментах")
public class DepartmentController {

    /**
     * Экземпляр сервиса для работы с данными о департаментах
     */
    private final DepartmentService departmentService;

    /**
     * Метод получения списка всех департаментов
     * @return список всех департаментов в формате JSON
     */
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

    /**
     * Метод добавления нового департамента и получения экземпляра новой записи
     * @param department объекта формата JSON, содержащий основную информацию для создания новой записи
     * @return объект формата JSON с набором данных о новой записи о департаменте
     */
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

    /**
     * Метод обновления данных о департаменте и получения актуального экземпляра записи
     * @param id номер департамента, который нужно обновить
     * @param department объекта формата JSON, содержащий основную информацию для обновления записи
     * @return объект формата JSON с набором данных об измененной записи о департаменте
     */
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

    /**
     * Метод удаления данных о департаменте
     * @param id номер департамента, информацию о котором нужно удалить / скрыть
     */
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