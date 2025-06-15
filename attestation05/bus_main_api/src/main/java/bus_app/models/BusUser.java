package bus_app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Класс - сущность объектов таблицы users
 */
@Entity
@Table(name = "bus_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusUser implements Serializable {

    /**
     * Имя пользователя
     */
    @Id
    private String username;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Список уровней доступа пользователя
     */
    private List<String> roles;
}