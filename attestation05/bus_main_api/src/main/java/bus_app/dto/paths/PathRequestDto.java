package bus_app.dto.paths;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Класс формата получения от пользователя информации о пути следования
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель запроса с информацией о пути следования")
public class PathRequestDto {

    /**
     * Номер пути
     */
    @Schema(description = "Номер пути следования")
    private String number;

    /**
     * Идентификатор начальной остановки
     */
    @Schema(description = "Идентификатор начальной станции")
    private Long beginStation;

    /**
     * Идентификатор конечной остановки
     */
    @Schema(description = "Идентификатор конечной станции")
    private Long endStation;

    /**
     * Список идентификаторов остановок на пути
     */
    @Schema(description = "Список идентификаторов всех станций на пути")
    private List<Long> stations;

    /**
     * Список времени от начала пути до конкретной остановки
     */
    @Schema(description = "Время о начала пути до каждой станции")
    private List<Long> timesFromStartToStations;

    /**
     * Продолжительность пути
     */
    @Schema(description = "Продолжительность пути")
    private Integer duration;
}