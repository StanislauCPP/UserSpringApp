package springEducation.service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfig {

	@Bean
	NewTopic createUserCreatedEventsTopic() {
		return TopicBuilder.name("user-created-events-topic")
			.partitions(3)
			.replicas(1)
			.build();
	}

	@Bean
	NewTopic createUserDeletedEventsTopic() {
		return TopicBuilder.name("user-deleted-events-topic")
				.partitions(3)
				.replicas(1)
				.build();
	}
}