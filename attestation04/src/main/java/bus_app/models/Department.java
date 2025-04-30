package bus_app.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс - сущность объектов таблицы departments
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Department {

    /**
     * Идентификатор департамента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название департамента
     */
    private String name;

    /**
     * Адрес департамента
     */
    private String address;

    /**
     * Запись удалена
     */
    private Boolean isDeleted;
}