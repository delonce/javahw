package app.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.logging.Logger;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class KafkaRouter {

    private final KafkaHandler handler;
    private final static Logger LOGGER = Logger.getLogger(KafkaRouter.class.getName());

    @Bean
    public RouterFunction<ServerResponse> getKafkaEndpoint() {
        return route()
                .before(request -> {
                    LOGGER.info(String.format("%s || %s || %s",
                            request.method(), request.path(), request.queryParams()));
                    return request;
                })
                .POST("kafka/send", handler::sendMessage)
                .build();
    }
}