package springEducation.service;

import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springEducation.dto.UserDto;
import springEducation.dto.mapper.UserMapper;
import springEducation.entity.User;
import springEducation.repository.UserCrudRepository;

import java.util.Optional;

@Data
@Service
public class UserService {

	@NonNull
	UserCrudRepository userCrudRepository;

	@NonNull
	KafkaProducerService kafkaProducerService;

	@Transactional
	public UserDto createUser(UserDto userDto) {
		User user = UserMapper.toEntity(userDto);

		user = userCrudRepository.save(user);
		kafkaProducerService.eventCreatedUser(userDto);

		return UserMapper.toDto(user);
	}

	@Transactional
	public UserDto searchUserById(Long id) {
		Optional<User> optionalUser = userCrudRepository.findById(id);
		return optionalUser.map(UserMapper::toDto).orElse(null);
	}

	@Transactional
	public UserDto updateUser(Long id, UserDto userDto) {
		Optional<User> optionalUser = userCrudRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = UserMapper.toEntity(userDto);
			user.setId(id);
			user.setCreatedAt(optionalUser.get().getCreatedAt());
			return UserMapper.toDto(userCrudRepository.save(user));
		}

		return null;
	}

	@Transactional
	public boolean deleteUser(Long id) {
		Optional<User> optionalUser = userCrudRepository.findById(id);
		if(optionalUser.isEmpty())
			return false;

		userCrudRepository.deleteById(id);
		kafkaProducerService.eventDeletedUser(UserMapper.toDto(optionalUser.get()));

		return true;
	}
}
