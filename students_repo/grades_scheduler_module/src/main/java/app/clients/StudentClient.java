package app.clients;

import app.models.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class StudentClient {

    private final RestClient client;

    public StudentClient(String baseUrl) {
        this.client = RestClient.create(baseUrl);
    }

    public List<Student> getStudentsById(Integer id) {
        return client.get()
                .uri(builder -> builder
                        .queryParam("grade_id", Integer.toString(id))
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}