package bus_app.dto.paths;

import bus_app.dto.stations.StationDto;
import bus_app.models.Path;
import bus_app.models.PathStation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Класс формата отправки пользователю информации о пути следования
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель ответа с информацией о пути следования")
public class PathResponseDto {

    /**
     * Номер пути следования
     */
    @Schema(description = "Номер пути следования")
    private String number;

    /**
     * Начальная остановка
     */
    @Schema(description = "Информация о начальной станции")
    private StationDto beginStation;

    /**
     * Конечная остановка
     */
    @Schema(description = "Информация о конечной станции")
    private StationDto endStation;

    /**
     * Список остановок на пути следования
     */
    @Schema(description = "Список всех станций на пути")
    private List<StationDto> stations;

    /**
     * Продолжительность пути
     */
    @Schema(description = "Продолжительность пути")
    private Integer duration;

    public PathResponseDto(Path path) {
        this.number = path.getNumber();
        this.beginStation = new StationDto(path.getBeginStation());
        this.endStation = new StationDto(path.getEndStation());
        this.stations = path.getStations().stream().map(PathStation::getStation).map(StationDto::new).toList();
        this.duration = path.getDuration();
    }
}