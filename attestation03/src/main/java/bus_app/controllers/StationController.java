package bus_app.controllers;

import bus_app.dto.stations.StationDto;
import bus_app.services.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
@Tag(name = "Остановки", description = "API для взаимодействия с записями об остановках")
public class StationController {

    private final StationService stationService;

    @Operation(
            summary = "Получить список всех остановок",
            description = "Возвращает список всех доступных остановок",
            tags = {"Остановки"}
    )
    @GetMapping
    public List<StationDto> getAllStations() {
        return stationService.readAllStations().stream()
                .map(StationDto::new).toList();
    }

    @Operation(
            summary = "Добавить в базу данных новую остановку",
            description = "Добавляет запись о новой остановке в базу данных и возвращает информацию о нем",
            tags = {"Остановки"}
    )
    @PostMapping
    public StationDto addStation(
            @Parameter(description = "Информация об остановке")
            @RequestBody StationDto station) {
        return new StationDto(stationService.addStation(station));
    }

    @Operation(
            summary = "Изменить информацию об остановке",
            description = "Изменяет информацию об остановке на предоставленную и возвращает измененную запись",
            tags = {"Остановки"}
    )
    @PutMapping("/{id}")
    public StationDto updateStation(
            @Parameter(description = "Идентификатор остановки")
            @PathVariable("id") Long id,
            @Parameter(description = "Информация об остановке")
            @RequestBody StationDto station) {
        return new StationDto(stationService.updateStation(id, station));
    }

    @Operation(
            summary = "Удалить запись об остановке",
            description = "Удаляет запись об остановке с предоставленным id",
            tags = {"Остановки"}
    )
    @DeleteMapping("/{id}")
    public void deleteStation(
            @Parameter(description = "Идентификатор остановки")
            @PathVariable("id") Long id) {
        stationService.deleteStation(id);
    }
}