package springEducation.doc;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@AllArgsConstructor
public class OpenAPIConfiguration {
	private Environment environment;

	@Bean
	public OpenAPI defineOpenAPI() {
		Server server = new Server();
		String serverUrl = environment.getProperty("api.server.url");
		server.setUrl(serverUrl);
		server.setDescription("Development");

		Info info = new Info()
			.title("RestController for work with users database")
			.version("0.1")
			.description("Database - postgreSQL");

		return new OpenAPI().info(info).servers(List.of(server));
	}
}
