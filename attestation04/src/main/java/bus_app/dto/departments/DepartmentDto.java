package bus_app.dto.departments;

import bus_app.models.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Класс формата получения от пользователя и отправки пользователю информации о департаменте
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Модель с информацией о департаменте")
public class DepartmentDto {

    /**
     * Название департамента
     */
    @Schema(description = "Название департамента")
    private String name;

    /**
     * Адрес департамента
     */
    @Schema(description = "Адрес департамента")
    private String address;

    public DepartmentDto(Department department) {
        this.name = department.getName();
        this.address = department.getAddress();
    }
}