package app.models.clients_data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EarthquakeFeature {

    private EarthquakeProperties properties;

    private String id;
}
