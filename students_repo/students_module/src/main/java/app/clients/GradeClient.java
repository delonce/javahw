package app.clients;

import app.exceptions.NoDataException;
import app.models.Grade;
import org.springframework.web.client.RestClient;

public class GradeClient {

    private final RestClient client;

    public GradeClient(String url) {
        client = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    public Grade readGrade(Integer id) throws NoDataException {
        return client.get()
                .uri(String.valueOf(id))
                .retrieve()
                .onStatus(status -> status.value() == 404, ((request, response) -> {
                    throw new NoDataException(response.getStatusText());
                }))
                .body(Grade.class);
    }

    public Grade makeGradeActive(Integer id) throws NoDataException {
        return client.put()
                .uri(String.valueOf(id))
                .retrieve()
                .onStatus(status -> status.value() == 404, ((request, response) -> {
                    throw new NoDataException(response.getStatusText());
                }))
                .body(Grade.class);
    }

    public Grade makeGradeNonActive(Integer id) throws NoDataException {
        return client.delete()
                .uri(String.valueOf(id))
                .retrieve()
                .onStatus(status -> status.value() == 404, ((request, response) -> {
                    throw new NoDataException(response.getStatusText());
                }))
                .body(Grade.class);
    }
}
