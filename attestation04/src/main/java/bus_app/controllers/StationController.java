package bus_app.controllers;

import bus_app.dto.stations.StationDto;
import bus_app.services.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, предоставляющего API для обращения к данным остановок
 */
@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
@Tag(name = "Остановки", description = "API для взаимодействия с записями об остановках")
public class StationController {

    /**
     * Экземпляр сервиса для работы с данными об остановках
     */
    private final StationService stationService;

    /**
     * Метод получения списка всех остановок
     * @return список всех остановок в формате JSON
     */
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

    /**
     * Метод добавления новой остановки и получения экземпляра новой записи
     * @param station объекта формата JSON, содержащий основную информацию для создания новой записи
     * @return объект формата JSON с набором данных о новой записи об остановке
     */
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

    /**
     * Метод обновления данных об остановке и получения актуального экземпляра записи
     * @param station объекта формата JSON, содержащий основную информацию для обновления записи
     * @return объект формата JSON с набором данных об измененной записи об остановке
     */
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

    /**
     * Метод удаления данных об остановке
     * @param id номер остановки, информацию о котором нужно удалить / скрыть
     */
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