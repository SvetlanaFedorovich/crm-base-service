package mvpproject.crmbaseservice.kafka.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class SendMessage {
    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public SendMessage(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Long id) {
        CompletableFuture<SendResult<String, Long>> future =
                kafkaTemplate.send("sales-updated-events-topic", "update", id);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                LOGGER.error("Failed to send message for Sales with Id: {}", id, exception.getMessage());
            } else {
                LOGGER.info("Topic: {}", result.getRecordMetadata().topic());
                LOGGER.info("Partition: {}", result.getRecordMetadata().partition());
                LOGGER.info("Offset: {}", result.getRecordMetadata().offset());
                LOGGER.info("Sales with Id: {} updated successfully", id);
            }
            LOGGER.info("Message sent successfully!");
        });
    }
}
