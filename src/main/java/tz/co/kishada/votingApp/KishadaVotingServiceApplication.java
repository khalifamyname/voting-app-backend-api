package tz.co.kishada.votingApp;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class KishadaVotingServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(KishadaVotingServiceApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(
			@Value("${openapi.service.title}") String serviceTitle,
			@Value("${openapi.service.version}") String serviceVersion,
			@Value("${openapi.service.url}") String url) {
		//assetService.readFileToBase64("1233.jpg");
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.servers(List.of(new Server().url(url)))
				.components(
						new Components()
								.addSecuritySchemes(
										securitySchemeName,
										new SecurityScheme()
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")))
				.security(List.of(new SecurityRequirement().addList(securitySchemeName)))
				.info(new Info().title(serviceTitle).version(serviceVersion));
	}

}
