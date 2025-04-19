package bus_app.dto.buses;

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
@Schema(description = "Модель запроса с информацией об автобусе")
public class BusRequestDto {

    @Schema(description = "Номер автобуса")
    private String number;

    @Schema(description = "Номер пути следования")
    private String path;

    @Schema(description = "Номер департамента")
    private Long department;

    @Schema(description = "Список идентификаторов водителей")
    private List<Long> drivers;

    @Schema(description = "Количество мест для посадки")
    private Integer seatsNumber;

    @Schema(description = "Тип питания автобуса")
    private String type;

    @Schema(description = "Эксплуатируется ли автобус")
    private Boolean isActive;
}