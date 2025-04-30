package bus_app.dto.stations;

import bus_app.models.Station;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Класс формата получения от пользователя и отправки пользователю информации об остановке
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель с информацией об остановке")
public class StationDto {

    /**
     * Название остановки
     */
    @Schema(description = "Название остановки")
    private String name;

    /**
     * Район остановки
     */
    @Schema(description = "Название района, в котором находится остановка")
    private String district;

    public StationDto(Station station) {
        this.name = station.getName();
        this.district = station.getDistrict();
    }
}