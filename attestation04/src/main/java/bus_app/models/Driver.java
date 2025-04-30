package bus_app.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс - сущность объектов таблицы drivers
 */
@Entity
@Table(name = "drivers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Driver {

    /**
     * Идентификатор водителя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ФИО водителя
     */
    private String name;

    /**
     * Возраст водителя
     */
    private Integer age;

    /**
     * Телефон водителя
     */
    private String phone;

    /**
     * Запись удалена
     */
    private Boolean isDeleted;
}