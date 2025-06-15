package bus_app.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - сущность объектов таблицы paths
 */
@Entity
@Table(name = "paths")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Path implements Serializable {

    /**
     * Номер пути следования
     */
    @Id
    private String number;

    /**
     * Начальная станция пути
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "begin_station", referencedColumnName = "id")
    private Station beginStation;

    /**
     * Конечная станция пути
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "end_station", referencedColumnName = "id")
    private Station endStation;

    /**
     * Список станции на пути следования
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "path", cascade = CascadeType.ALL)
    private List<PathStation> stations = new ArrayList<>();

    /**
     * Протяженность пути
     */
    private Integer duration;

    /**
     * Запись удалена
     */
    private Boolean isDeleted;
}