package bus_app.controllers;

import bus_app.dto.buses.BusRequestDto;
import bus_app.dto.buses.BusResponseDto;
import bus_app.services.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс контроллера, предоставляющего API для обращения к данным автобусов
 */
@RestController
@RequestMapping("/api/v1/buses")
@RequiredArgsConstructor
@Tag(name = "Автобусы", description = "API для взаимодействия с записями об автобусах")
public class BusController {

    /**
     * Экземпляр сервиса для работы с данными об автобусах
     */
    private final BusService busService;

    /**
     * Метод получения списка всех автобусов
     * @return список всех автобусов в формате JSON
     */
    @Operation(
            summary = "Получить список всех автобусов",
            description = "Возвращает список всех доступных автобусов с сопутствующей им информацией о водителям, " +
                    "путях следования и департаментах",
            tags = {"Автобусы"}
    )
    @GetMapping
    public List<BusResponseDto> getAllBuses() {
        return busService.readAllBuses().stream()
                .map(BusResponseDto::new)
                .toList();
    }

    /**
     * Метод добавления нового автобуса и получения экземпляра новой записи
     * @param bus объекта формата JSON, содержащий основную информацию для создания новой записи
     * @return объект формата JSON с набором данных о новой записи об автобусе
     */
    @Operation(
            summary = "Добавить в базу данных новый автобус",
            description = "Добавляет запись о новом автобусе в базу данных и возвращает информацию о нем",
            tags = {"Автобусы"}
    )
    @PostMapping
    public BusResponseDto addBus(
            @Parameter(description = "Информация об автобусе")
            @RequestBody BusRequestDto bus) {
        return new BusResponseDto(busService.createBus(bus));
    }

    /**
     * Метод обновления данных об автобусе и получения актуального экземпляра записи
     * @param bus объекта формата JSON, содержащий основную информацию для обновления записи
     * @return объект формата JSON с набором данных об измененной записи об автобусе
     */
    @Operation(
            summary = "Изменить информацию об автобусе",
            description = "Изменяет информацию об автобусе на предоставленную и возвращает измененную запись",
            tags = {"Автобусы"}
    )
    @PutMapping
    public BusResponseDto updateBus(
            @Parameter(description = "Информация об автобусе")
            @RequestBody BusRequestDto bus) {
        return new BusResponseDto(busService.updateBus(bus));
    }

    /**
     * Метод удаления данных об автобусе
     * @param number номер автобуса, информацию о котором нужно удалить / скрыть
     */
    @Operation(
            summary = "Удалить запись об автобусе",
            description = "Удаляет запись об автобусе с предоставленным номером",
            tags = {"Автобусы"}
    )
    @DeleteMapping
    public void deleteBus(
            @Parameter(description = "Номер автобуса")
            @RequestBody String number) {
        busService.deleteBus(number);
    }
}