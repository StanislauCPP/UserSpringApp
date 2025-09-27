package springEducation.dto;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

	@Nullable
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String email;

	@NonNull
	private Integer age;

	private LocalDate createdAt = LocalDate.now();
}
