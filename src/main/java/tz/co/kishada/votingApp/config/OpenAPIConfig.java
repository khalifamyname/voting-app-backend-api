package tz.co.kishada.votingApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-${spring.profiles.active}.yml")
@OpenAPIDefinition(
        info = @Info(
                title = "ikmis-agtif-service",
                version = "v1"
        ),
        servers = @Server(url = "/ikmis-agtif-service")

)
public class OpenAPIConfig {
}
