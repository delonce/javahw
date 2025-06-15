package bus_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс - сущность объектов таблицы paths_stations
 */
@Entity
@Table(name = "paths_stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PathStation implements Serializable {

    /**
     * Путь следования
     */
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "path_number")
    private Path path;

    /**
     * Остановка на пути следования
     */
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "station_id")
    private Station station;

    /**
     * Время, которое необходимо, чтобы доехать от начала пути до текущей остановки
     */
    private Long timeSpentFromStart;
}