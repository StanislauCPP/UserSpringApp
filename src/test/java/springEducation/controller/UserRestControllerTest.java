package springEducation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import springEducation.dto.UserDto;
import springEducation.service.UserService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	UserService userService;

	private UserDto userDtoFromRepository() {
		UserDto userDto = new UserDto("Vasiliy", "Vaska@email.net", 59);
		userDto.setId(50L);
		userDto.setCreatedAt(LocalDate.of(2010, 12, 12));

		return userDto;
	}

	private UserDto userDtoInput() { return new UserDto("Vasiliy", "Vaska@email.net", 59); }

	@Test
	void userDtoInput_createUserRequest_expectedResponseWithOkStatusAndJsonUserDto() throws Exception {
		UserDto userDtoExpected = userDtoFromRepository();
		UserDto userDtoInput = userDtoInput();
		Mockito.when(userService.createUser(userDtoInput)).thenReturn(userDtoExpected);

		mvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDtoInput))).andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(userDtoExpected.getId()))
			.andExpect(jsonPath("$.name").value(userDtoExpected.getName()))
			.andExpect(jsonPath("$.email").value(userDtoExpected.getEmail()))
			.andExpect(jsonPath("$.age").value(userDtoExpected.getAge()))
			.andExpect(jsonPath("$.createdAt").value(userDtoExpected.getCreatedAt().toString()));
	}

	@Test
	void notExistedUserDtoInput_createUserRequest_expectedResponseWithBadRequestStatus() throws Exception {
		Mockito.when(userService.createUser(userDtoInput())).thenThrow(new DataIntegrityViolationException(null));

		mvc.perform(post("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDtoInput())))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void userIdInput_findUserByIdRequest_expectedResponseWithOkStatusAndJsonUserDto() throws Exception {
		Long id = 50L;
		UserDto userDtoExpected = userDtoFromRepository();

		Mockito.when(userService.searchUserById(id)).thenReturn(userDtoExpected);

		mvc.perform(get("/users/%d".formatted(id)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(userDtoExpected.getId()))
			.andExpect(jsonPath("$.name").value(userDtoExpected.getName()))
			.andExpect(jsonPath("$.email").value(userDtoExpected.getEmail()))
			.andExpect(jsonPath("$.age").value(userDtoExpected.getAge()))
			.andExpect(jsonPath("$.createdAt").value(userDtoExpected.getCreatedAt().toString()));
	}

	@Test
	public void notExistedUserIdInput_findUserByIdRequest_expectedNotFoundStatus() throws Exception {
		Mockito.when(userService.searchUserById(5L)).thenReturn(null);

		mvc.perform(get("/users/100"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updatedUserInput_UpdateUserRequest_expectedResponseWithOkStatusAndJsonUserDto() throws Exception {
		UserDto userDtoExpected = userDtoFromRepository();
		UserDto userDtoInput = userDtoInput();
		Long id = 50L;

		Mockito.when(userService.updateUser(id, userDtoInput)).thenReturn(userDtoExpected);

		mvc.perform(put("/users/%d".formatted(id))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDtoInput)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(userDtoExpected.getId()))
				.andExpect(jsonPath("$.name").value(userDtoExpected.getName()))
				.andExpect(jsonPath("$.email").value(userDtoExpected.getEmail()))
				.andExpect(jsonPath("$.age").value(userDtoExpected.getAge()))
				.andExpect(jsonPath("$.createdAt").value(userDtoExpected.getCreatedAt().toString()));
	}

	@Test
	public void notExistUserInput_UpdateUserRequest_expectedResponseWithNotFoundStatus() throws Exception {
		UserDto userDtoInput = userDtoInput();
		Long id = 100L;

		Mockito.when(userService.updateUser(id, userDtoInput)).thenReturn(null);

		mvc.perform(put("/users/%d".formatted(id))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDtoInput)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void userIdInput_deleteUserByIdRequest_expectedResponseWithOkStatus() throws Exception {
		Long id = 50L;

		Mockito.when(userService.deleteUser(id)).thenReturn(true);

		mvc.perform(delete("/users/%d".formatted(id))
			.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void notExistedUserIdInput_deleteUserByIdRequest_expectedResponseWithNotFound() throws Exception {
		Long id = 100L;

		Mockito.when(userService.deleteUser(id)).thenReturn(false);

		mvc.perform(delete("/users/%d".formatted(id))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());

	}
}
