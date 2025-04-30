package bus_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Класс - сущность объектов таблицы buses
 */
@Entity
@Table(name = "buses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Bus {

    /**
     * Номер автобуса
     */
    @Id
    private String number;

    /**
     * Путь следования автобуса
     */
    @OneToOne
    @JoinColumn(name = "path_number", referencedColumnName = "number")
    private Path path;

    /**
     * Департамент, к которому относится автобус
     */
    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    /**
     * Список водителей автобуса
     */
    @ManyToMany
    @JoinTable(
            name = "buses_drivers",
            joinColumns = @JoinColumn(name = "bus_number"),
            inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    private List<Driver> drivers;

    /**
     * Количество сидячих мест
     */
    private Integer seatsNumber;

    /**
     * Тип питания автобуса
     */
    private String type;

    /**
     * Автобус в строю / на обслуживании
     */
    private Boolean isActive;

    /**
     * Запись удалена
     */
    private Boolean isDeleted;
}