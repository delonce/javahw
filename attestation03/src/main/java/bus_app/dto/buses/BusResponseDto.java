package bus_app.dto.buses;

import bus_app.dto.departments.DepartmentDto;
import bus_app.dto.drivers.DriverDto;
import bus_app.dto.paths.PathResponseDto;
import bus_app.models.Bus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Модель ответа с информацией об автобусе")
public class BusResponseDto {

    @Schema(description = "Номер автобуса")
    private String number;

    @Schema(description = "Путь следования")
    private PathResponseDto path;

    @Schema(description = "Департамент")
    private DepartmentDto department;

    @Schema(description = "Список водителей")
    private List<DriverDto> drivers;

    @Schema(description = "Количество мест для посадки")
    private Integer seatsNumber;

    @Schema(description = "Тип питания автобуса")
    private String type;

    @Schema(description = "Эксплуатируется ли автобус")
    private Boolean isActive;

    public BusResponseDto(Bus bus) {
        this.number = bus.getNumber();
        this.path = new PathResponseDto(bus.getPath());
        this.department = new DepartmentDto(bus.getDepartment());
        this.drivers = bus.getDrivers().stream().map(DriverDto::new).toList();
        this.seatsNumber = bus.getSeatsNumber();
        this.isActive = bus.getIsActive();
    }
}