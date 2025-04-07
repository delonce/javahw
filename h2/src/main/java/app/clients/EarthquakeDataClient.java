package app.clients;

import app.models.clients_data.EarthquakeFeatureCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class EarthquakeDataClient {

    private final RestClient client;

    public EarthquakeFeatureCollection getData() {
        return client.get()
                .retrieve()
                .body(EarthquakeFeatureCollection.class);
    }
}