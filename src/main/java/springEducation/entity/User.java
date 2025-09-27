package springEducation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name_", nullable = false)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "age", nullable = false)
	private int age;
	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;
}