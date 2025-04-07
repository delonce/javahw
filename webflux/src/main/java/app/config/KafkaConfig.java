package app.config;

import app.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ObjectMapper mapper;
    private final KafkaProperties properties;

    @Bean
    public NewTopic getMessagesTopic() {
        return TopicBuilder.name("message_topic").build();
    }

    @Bean
    public SenderOptions<String, MessageDto> getSenderOptions() {
        return SenderOptions.<String, MessageDto> create(properties.buildProducerProperties(null))
                .producerProperty(JsonSerializer.ADD_TYPE_INFO_HEADERS, false)
                .withKeySerializer(new StringSerializer())
                .withValueSerializer(new JsonSerializer<>());
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, MessageDto> getKafkaProducer(
            SenderOptions<String, MessageDto> options
    ) {
        return new ReactiveKafkaProducerTemplate<>(options);
    }

    @Bean
    public ReceiverOptions<String, MessageDto> getReceiverOptions() {
        return ReceiverOptions.<String, MessageDto> create(properties.buildConsumerProperties(null))
                .subscription(List.of("message_topic"))
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new JsonDeserializer<>(MessageDto.class, mapper))
                .consumerProperty(JsonDeserializer.TRUSTED_PACKAGES, "*");
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, MessageDto> getKafkaConsumer(
            ReceiverOptions<String, MessageDto> options
    ) {
        return new ReactiveKafkaConsumerTemplate<>(options);
    }
}