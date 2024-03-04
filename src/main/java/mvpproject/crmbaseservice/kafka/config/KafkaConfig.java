package mvpproject.crmbaseservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic createTopic() {
        return TopicBuilder.name("sales-updated-events-topic")
                .partitions(1)
                .replicas(1)
                .configs(Map.of("min.insync.replicas", "1"))
                .build();
    }
}

