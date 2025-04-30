package bus_app.dto.drivers;

import bus_app.models.Driver;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Класс формата получения от пользователя и отправки пользователю информации о водителе
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель с информацией о водителе")
public class DriverDto {

    /**
     * ФИО водителя
     */
    @Schema(description = "ФИО водителя")
    private String name;

    /**
     * Возраст водителя
     */
    @Schema(description = "Возраст водителя")
    private Integer age;

    /**
     * Телефон водителя
     */
    @Schema(description = "Телефон водителя")
    private String phone;

    public DriverDto(Driver driver) {
        this.name = driver.getName();
        this.age = driver.getAge();
        this.phone = driver.getPhone();
    }
}