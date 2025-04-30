package bus_app.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс - сущность объектов таблицы stations
 */
@Entity
@Table(name = "stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Station {

    /**
     * Идентификатор остановки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название остановки
     */
    private String name;

    /**
     * Район остановки
     */
    private String district;

    /**
     * Запись удалена
     */
    private Boolean isDeleted;
}