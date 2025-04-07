package app.clients;

import app.models.Comment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentClient {

    private final RestClient client;

    public CommentClient(String url) {
        client = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    public Comment addComment(Integer student, Integer grade, String text) {
        return client.post()
                .uri(builder -> builder
                        .queryParam("student", student.toString())
                        .queryParam("grade", grade.toString())
                        .build())
                .body(text)
                .retrieve()
                .body(Comment.class);
    }

    public List<Comment> getCommentsByStudent(Integer studentId) {
        return client.get()
                .uri("student/" + studentId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<Comment> getCommentsByGrade(Integer gradeId) {
        return client.get()
                .uri("grade/" + gradeId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}