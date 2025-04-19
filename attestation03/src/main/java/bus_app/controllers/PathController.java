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

@RestController
@RequestMapping("/api/v1/paths")
@RequiredArgsConstructor
@Tag(name = "Пути следования", description = "API для взаимодействия с записями о путях следования автобусов")
public class PathController {

    private final PathService pathService;

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

    @Operation(
            summary = "Изменить информацию о пути",
            description = "Изменяет информацию о пути на предоставленную и возвращает измененную запись",
            tags = {"Пути следования"}
    )
    @PutMapping
    public PathResponseDto updatePath(
            @Parameter(description = "Информация о пути следования")
            @RequestBody PathRequestDto path) {
        return new PathResponseDto(pathService.updatepath(path));
    }

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