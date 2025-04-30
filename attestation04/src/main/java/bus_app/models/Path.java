package bus_app.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
public class Path {

    /**
     * Номер пути следования
     */
    @Id
    private String number;

    /**
     * Начальная станция пути
     */
    @OneToOne
    @JoinColumn(name = "begin_station", referencedColumnName = "id")
    private Station beginStation;

    /**
     * Конечная станция пути
     */
    @OneToOne
    @JoinColumn(name = "end_station", referencedColumnName = "id")
    private Station endStation;

    /**
     * Список станции на пути следования
     */
    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL)
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