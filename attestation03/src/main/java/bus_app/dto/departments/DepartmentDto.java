package bus_app.dto.departments;

import bus_app.models.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель с информацией о департаменте")
public class DepartmentDto {

    @Schema(description = "Название департамента")
    private String name;

    @Schema(description = "Адрес департамента")
    private String address;

    public DepartmentDto(Department department) {
        this.name = department.getName();
        this.address = department.getAddress();
    }
}