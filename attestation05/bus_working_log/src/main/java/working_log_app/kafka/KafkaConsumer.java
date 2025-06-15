package working_log_app.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import working_log_app.service.WorkingLogService;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final WorkingLogService service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "message_topic", groupId = "messages_group")
    public void processMessage(String message) throws JsonProcessingException {
        KafkaMessageDto messageDto = mapper.readValue(message, KafkaMessageDto.class);

        if (messageDto.getMode().equals("ON")) {
            service.createNewLog(messageDto);
        } else {
            service.closeLog(messageDto);
        }
    }
}