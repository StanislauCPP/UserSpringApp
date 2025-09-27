package springEducation.dto.mapper;

import springEducation.dto.UserDto;
import springEducation.entity.User;

public class UserMapper {
	public static UserDto toDto(User user) {
		UserDto userDto = new UserDto(user.getName(), user.getEmail(), user.getAge());
		userDto.setCreatedAt(user.getCreatedAt());
		userDto.setId(user.getId());
		return userDto;
	}

	public static User toEntity(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAge(userDto.getAge());
		user.setCreatedAt(userDto.getCreatedAt());

		return user;
	}
}
