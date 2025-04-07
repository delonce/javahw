package app.clients;

import app.models.Grade;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class GradeClient {

    private final RestClient client;

    public GradeClient(String baseUrl) {
        this.client = RestClient.create(baseUrl);
    }

    public List<Grade> getGrades() {
        return client.get()
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void makeGradeActive(Integer id) {
        client.delete()
                .uri("/" + id)
                .retrieve();
    }
}