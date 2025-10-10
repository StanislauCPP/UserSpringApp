package springEducation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Successful creation user"),
		@ApiResponse(responseCode = "400", description = "User not created")
	})

	@Operation(summary = "Create new user in db")
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		try {
			UserDto result = userService.createUser(userDto);
			return new ResponseEntity<UserDto>(result, HttpStatus.CREATED);
		}
		catch (DataIntegrityViolationException e) {	return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST); }
	}

	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "User found"),
		@ApiResponse(responseCode = "404", description = "User not found")
	})

	@Operation(summary = "Get user from db")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long id) {
		UserDto userDto = userService.searchUserById(id);

		if(userDto == null)
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Successful updating"),
		@ApiResponse(responseCode = "404", description = "User not found")
	})

	@Operation(summary = "Update user from db", description = "Change some user parameters")
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
		UserDto result = userService.updateUser(id, userDto);

		if(result == null)
			return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<UserDto>(result, HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Successful removing"),
		@ApiResponse(responseCode = "404", description = "User not found")
	})

	@Operation(summary = "Delete user from db")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		Boolean result = userService.deleteUser(id);

		if(result == false)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}