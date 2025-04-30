package bus_app.dto.bus_users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс формата получения информации об авторизационных данных пользователя
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель запроса с информацией о пользователе с правами доступа")
public class BusUserDto {

    /**
     * Имя пользователя
     */
    @Schema(description = "Имя пользователя")
    private String username;

    /**
     * Пароль пользователя
      */
    @Schema(description = "Пароль пользователя")
    private String password;
}