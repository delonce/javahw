package bus_app.dto.stations;

import bus_app.models.Station;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель с информацией об остановке")
public class StationDto {

    @Schema(description = "Название остановки")
    private String name;

    @Schema(description = "Название района, в котором находится остановка")
    private String district;

    public StationDto(Station station) {
        this.name = station.getName();
        this.district = station.getDistrict();
    }
}