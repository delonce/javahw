package app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class EarthquakeDataClientConfig {

    @Value("${earthquakes.datasource.url}")
    private String earthquakeDataUrl;

    @Bean
    public RestClient getClient() {
        return RestClient.builder()
                .baseUrl(earthquakeDataUrl)
                .build();
    }
}