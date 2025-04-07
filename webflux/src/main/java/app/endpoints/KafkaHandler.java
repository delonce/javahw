package app.endpoints;

import app.kafka.MessagesProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KafkaHandler {

    private final MessagesProducer producer;

    public Mono<ServerResponse> sendMessage(ServerRequest request) {
        Integer count = Integer.parseInt(
                request.queryParam("count")
                        .orElse("10")
        );

        String text = request.queryParam("text")
                .orElse("Default message");

        return producer.send(text, count)
                .then()
                .flatMap(result -> ServerResponse.ok()
                        .body(Mono.just("The message has been sent!"), String.class));
    }
}