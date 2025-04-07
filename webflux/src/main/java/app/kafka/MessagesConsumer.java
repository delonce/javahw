package app.kafka;

import app.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MessagesConsumer {

    private final ReactiveKafkaConsumerTemplate<String, MessageDto> consumerTemplate;
    private final static Logger LOGGER = Logger.getLogger(MessagesConsumer.class.getName());

    @EventListener(ApplicationReadyEvent.class)
    public void processMessage() {
        consumerTemplate
                .receiveAutoAck()
                .doOnNext(message -> {
                    MessageDto messageDto = message.value();
                    LOGGER.info("Message №" + messageDto.getId() + " was received: " + messageDto.getText());
                })
                .doOnComplete(() -> LOGGER.info("Messages processing complete!"))
                .subscribe();
    }
}