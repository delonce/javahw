package bus_app.controllers;

import bus_app.dto.paths.PathRequestDto;
import bus_app.dto.paths.PathResponseDto;
import bus_app.services.PathService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, предоставляющего API для обращения к данным путей следования
 */
@RestController
@RequestMapping("/api/v1/paths")
@RequiredArgsConstructor
@Tag(name = "Пути следования", description = "API для взаимодействия с записями о путях следования автобусов")
public class PathController {

    /**
     * Экземпляр сервиса для работы с данными о путях следования
     */
    private final PathService pathService;

    /**
     * Метод получения списка всех путей
     * @return список всех путей в формате JSON
     */
    @Operation(
            summary = "Получить список всех путей",
            description = "Возвращает список всех доступных путей с сопутствующей им информацией о станциях",
            tags = {"Пути следования"}
    )
    @GetMapping
    public List<PathResponseDto> getAllPaths() {
        return pathService.readAllPaths().stream()
                .map(PathResponseDto::new).toList();
    }

    /**
     * Метод добавления нового пути и получения экземпляра новой записи
     * @param path объекта формата JSON, содержащий основную информацию для создания новой записи
     * @return объект формата JSON с набором данных о новой записи о пути
     */
    @Operation(
            summary = "Добавить в базу данных новый путь",
            description = "Добавляет запись о новом путе в базу данных и возвращает информацию о нем",
            tags = {"Пути следования"}
    )
    @PostMapping
    public PathResponseDto addPath(
            @Parameter(description = "Информация о пути следования")
            @RequestBody PathRequestDto path) {
        return new PathResponseDto(pathService.addPath(path));
    }

    /**
     * Метод обновления данных о пути и получения актуального экземпляра записи
     * @param path объекта формата JSON, содержащий основную информацию для обновления записи
     * @return объект формата JSON с набором данных об измененной записи о пути
     */
    @Operation(
            summary = "Изменить информацию о пути",
            description = "Изменяет информацию о пути на предоставленную и возвращает измененную запись",
            tags = {"Пути следования"}
    )
    @PutMapping
    public PathResponseDto updatePath(
            @Parameter(description = "Информация о пути следования")
            @RequestBody PathRequestDto path) {
        return new PathResponseDto(pathService.updatePath(path));
    }

    /**
     * Метод удаления данных о пути
     * @param number номер пути, информацию о котором нужно удалить / скрыть
     */
    @Operation(
            summary = "Удалить запись о пути следования",
            description = "Удаляет запись о пути с предоставленным номером",
            tags = {"Автобусы"}
    )
    @DeleteMapping
    public void deletePath(
            @Parameter(description = "Номер пути следования")
            @RequestBody String number) {
        pathService.deletePath(number);
    }
}