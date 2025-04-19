package bus_app.dto.paths;

import bus_app.dto.stations.StationDto;
import bus_app.models.Path;
import bus_app.models.PathStation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель ответа с информацией о пути следования")
public class PathResponseDto {

    @Schema(description = "Номер пути следования")
    private String number;

    @Schema(description = "Информация о начальной станции")
    private StationDto beginStation;

    @Schema(description = "Информация о конечной станции")
    private StationDto endStation;

    @Schema(description = "Список всех станций на пути")
    private List<StationDto> stations;

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