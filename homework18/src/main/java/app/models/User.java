package app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Класс - сущность объектов таблицы users
 * @author Danma
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /** Поле, содержащее имя пользователя */
    @Id
    @Column(name = "username")
    private String username;

    /** Поле, содержащее пароль пользователя */
    @Column(name = "password")
    private String password;

    /** Поле, содержащее уровни доступа пользователя */
    @Column(name = "roles")
    private List<String> roles;
}