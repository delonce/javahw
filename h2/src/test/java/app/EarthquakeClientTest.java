package app;

import app.clients.EarthquakeDataClient;
import app.configs.EarthquakeDataClientConfig;
import app.models.clients_data.EarthquakeFeatureCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EarthquakeDataClient.class, EarthquakeDataClientConfig.class})
public class EarthquakeClientTest {

    @Autowired
    private EarthquakeDataClient client;

    @Test
    @DisplayName("Client: resource check")
    public void getDataTest() {
        EarthquakeFeatureCollection collection = client.getData();

        assert !collection.getFeatures().isEmpty();
    }
}