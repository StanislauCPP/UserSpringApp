package springEducation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(description = "User data transfer object")
public class UserDto {

	@Schema(description = "Primary key", example = "5", accessMode = Schema.AccessMode.READ_ONLY)
	@Nullable
	private Long id;

	@Schema(description = "User name", example = "Vasya")
	@NonNull
	private String name;

	@Schema(description = "User email", example = "vasya@email.net")
	@NonNull
	private String email;

	@Schema(description = "User age", example = "50")
	@NonNull
	private Integer age;

	@Schema(description = "Date of creating user", example = "2025-10-10")
	private LocalDate createdAt = LocalDate.now();
}
