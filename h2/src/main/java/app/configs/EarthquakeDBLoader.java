package app.configs;

import app.clients.EarthquakeDataClient;
import app.models.clients_data.EarthquakeFeature;
import app.models.clients_data.EarthquakeFeatureCollection;
import app.models.entities.EarthquakeEntity;
import app.repositories.EarthquakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EarthquakeDBLoader implements CommandLineRunner {

    private final EarthquakeDataClient client;
    private final EarthquakeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        EarthquakeFeatureCollection collection = client.getData();

        for (EarthquakeFeature feature: collection.getFeatures()) {
            repository.save(EarthquakeEntity
                    .makeEntityFromProperties(feature.getProperties()));
        }

        repository.flush();
    }
}