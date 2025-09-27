package springEducation.controller;

import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springEducation.dto.UserDto;
import springEducation.service.UserService;

@Data
@RestController
@RequestMapping("/users")
public class UserRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	@NonNull
	UserService userService;

	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		try {
			UserDto result = userService.createUser(userDto);
			return new ResponseEntity<UserDto>(result, HttpStatus.CREATED);
		}
		catch (DataIntegrityViolationException e) {	return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST); }
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long id) {
		UserDto userDto = userService.searchUserById(id);

		if(userDto == null)
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
		UserDto result = userService.updateUser(id, userDto);

		if(result == null)
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<UserDto>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		Boolean result = userService.deleteUser(id);

		if(result == false)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}