package co.edu.unbosque.queboleteo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()

				.info(new Info()

						.title("API REST - QueBoleteo")

						.version("1.0")

						.description("""
								API REST del proyecto QueBoleteo.

								Este sistema permite gestionar:
								- artistas individuales,
								- grupos musicales,
								- eventos,
								- organizadores,
								- lugares,
								- boletería y reservas.

								La API se encuentra actualmente en desarrollo.
								"""));
	}

}