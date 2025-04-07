package app.models.entities;

import app.models.clients_data.EarthquakeProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EarthquakeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private LocalDateTime time;

    private Double mag;

    private String place;

    public static EarthquakeEntity makeEntityFromProperties(EarthquakeProperties properties) {
        return EarthquakeEntity.builder()
                .title(properties.getTitle())
                .time(properties.getTime().toLocalDateTime())
                .mag(properties.getMag())
                .place(properties.getPlace())
                .build();
    }
}