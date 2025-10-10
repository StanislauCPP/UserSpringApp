package springEducation.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import springEducation.dto.UserDto;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class KafkaProducerService {
	@NonNull
	KafkaTemplate<String, String> eventKafkaTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

	public void eventCreatedUser(UserDto userDto) {
		CompletableFuture<SendResult<String, String>> completableFuture = eventKafkaTemplate.send("user-created-events-topic", "Created", userDto.getEmail());

		completableFuture.whenComplete((r, e) -> {
			if(e != null)
				LOGGER.error("Failed to send user created event: {}", e.getMessage());
			else
				LOGGER.info("Successful send user created event: {}", r.getRecordMetadata());
		});
	}

	public void eventDeletedUser(UserDto userDto) {
		CompletableFuture<SendResult<String, String>> completableFuture = eventKafkaTemplate.send("user-deleted-events-topic", "Deleted", userDto.getEmail());

		completableFuture.whenComplete((r, e) -> {
			if(e != null)
				LOGGER.error("Failed to send user deleted event: {}", e.getMessage());
			else
				LOGGER.info("Successful send user deleted event: {}", r.getRecordMetadata());
		});
	}
}
