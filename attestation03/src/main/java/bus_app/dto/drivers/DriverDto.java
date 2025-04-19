package bus_app.dto.drivers;

import bus_app.models.Driver;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель с информацией о водителе")
public class DriverDto {

    @Schema(description = "ФИО водителя")
    private String name;

    @Schema(description = "Возраст водителя")
    private Integer age;

    @Schema(description = "Телефон водителя")
    private String phone;

    public DriverDto(Driver driver) {
        this.name = driver.getName();
        this.age = driver.getAge();
        this.phone = driver.getPhone();
    }
}