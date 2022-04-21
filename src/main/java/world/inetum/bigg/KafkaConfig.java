package world.inetum.bigg;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic1(@Value("${spring.kafka.template.default-topic}") String defaultTopic) {
        return TopicBuilder.name(defaultTopic)
                .compact()
                .build();
    }
}
