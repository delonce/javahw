package app.models.clients_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EarthquakeProperties {

    private Double mag;

    private String place;

    private Timestamp time;

    private String title;
}
