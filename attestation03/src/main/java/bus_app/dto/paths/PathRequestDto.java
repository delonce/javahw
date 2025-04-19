package bus_app.dto.paths;

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
@Schema(description = "Модель запроса с информацией о пути следования")
public class PathRequestDto {

    @Schema(description = "Номер пути следования")
    private String number;

    @Schema(description = "Идентификатор начальной станции")
    private Long beginStation;

    @Schema(description = "Идентификатор конечной станции")
    private Long endStation;

    @Schema(description = "Список идентификаторов всех станций на пути")
    private List<Long> stations;

    @Schema(description = "Время о начала пути до каждой станции")
    private List<Long> timesFromStartToStations;

    @Schema(description = "Продолжительность пути")
    private Integer duration;
}