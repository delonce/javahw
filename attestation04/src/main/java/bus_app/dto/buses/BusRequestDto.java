package bus_app.dto.buses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * Класс формата получения от клиента данных об автобусе
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель запроса с информацией об автобусе")
public class BusRequestDto {

    /**
     * Номер автобуса
     */
    @Schema(description = "Номер автобуса")
    private String number;

    /**
     * Номер пути следования
     */
    @Schema(description = "Номер пути следования")
    private String path;

    /**
     * Идентификатор департамента
     */
    @Schema(description = "Номер департамента")
    private Long department;

    /**
     * Список идентификаторов водителей
     */
    @Schema(description = "Список идентификаторов водителей")
    private List<Long> drivers;

    /**
     * Количество сидячих мест
     */
    @Schema(description = "Количество мест для посадки")
    private Integer seatsNumber;

    /**
     * Тип питания автобуса
     */
    @Schema(description = "Тип питания автобуса")
    private String type;

    /**
     * Автобус в строю / на обслуживании
     */
    @Schema(description = "Эксплуатируется ли автобус")
    private Boolean isActive;
}