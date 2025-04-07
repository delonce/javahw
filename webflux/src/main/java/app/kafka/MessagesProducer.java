package app.kafka;

import app.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.SenderResult;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MessagesProducer {

    private final ReactiveKafkaProducerTemplate<String, MessageDto> producerTemplate;
    private final static Logger LOGGER = Logger.getLogger(MessagesProducer.class.getName());

    public Flux<SenderResult<Void>> send(String text, Integer count) {
        return Flux.range(0, count)
                .map(i -> {
                    MessageDto message = new MessageDto(
                          i, text, LocalDateTime.now()
                    );

                    return MessageBuilder.withPayload(message)
                            .setHeader(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                            .build();
                })
                .flatMap(message -> {
                    LOGGER.info("Message " + message.getPayload().getId() + " has been sent to Kafka!");
                    return producerTemplate.send("message_topic", message);
                });
    }
}