package bus_app.controllers;

import bus_app.dto.drivers.DriverDto;
import bus_app.services.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
@Tag(name = "Водители", description = "API для взаимодействия с записями о водителях")
public class DriverController {

    private final DriverService driverService;

    @Operation(
            summary = "Получить список всех водителей",
            description = "Возвращает список всех доступных водителей",
            tags = {"Водители"}
    )
    @GetMapping
    public List<DriverDto> getAllDrivers() {
        return driverService.readAllDrivers().stream()
                .map(DriverDto::new).toList();
    }

    @Operation(
            summary = "Добавить в базу данных нового водителя",
            description = "Добавляет запись о новом водителе в базу данных и возвращает информацию о нем",
            tags = {"Водители"}
    )
    @PostMapping
    public DriverDto addDriver(
            @Parameter(description = "Информации о водителе")
            @RequestBody DriverDto driver) {
        return new DriverDto(driverService.addDriver(driver));
    }

    @Operation(
            summary = "Изменить информацию о водителе",
            description = "Изменяет информацию о водителе на предоставленную и возвращает измененную запись",
            tags = {"Водители"}
    )
    @PutMapping("/{id}")
    public DriverDto updateDriver(
            @Parameter(description = "Идентификатор водителя")
            @PathVariable("id") Long id,
            @Parameter(description = "Информация о водителе")
            @RequestBody DriverDto driverDto) {
        return new DriverDto(driverService.updateDriver(id, driverDto));
    }

    @Operation(
            summary = "Удалить запись о водителе",
            description = "Удаляет запись о водителе с предоставленным id",
            tags = {"Водители"}
    )
    @DeleteMapping("/{id}")
    public void deleteDriver(
            @Parameter(description = "Идентификатор водителя")
            @PathVariable("id") Long id) {
        driverService.deleteDriver(id);
    }
}