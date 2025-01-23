package tz.co.kishada.votingApp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${introspector.uri}")
	private String introspectUri;

	@Value("${uaa.client-id}")
	private String introspectClientId;

	@Value("${uaa.client-secret}")
	private String introspectClientSecret;

	private final CustomOpaqueTokenIntrospector introspector;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(
								"/api/v1/attachment/image/**",
								"/api/v1/attachment/path/**",
								"/ikmis-agtif-service/**",
								"/ikmis-agtif-service/v3/api-docs",
								"/ikmis-agtif-service/swagger-ui.html",
								"/v3/api-docs",
								"swagger-ui.html",
								"v3/**",
								"/swagger-ui/**",
								"/api/v1/portal/**",
								"/api/v1/portal/**",
								"/api/v1/integrator/**"
						)
						.permitAll().anyRequest()
						.authenticated())
				.oauth2ResourceServer(oauth2ResourceServer ->
						oauth2ResourceServer.opaqueToken(opaqueToken ->
								opaqueToken.introspector(introspector)
						)
				);
		return http.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				// Permit all access to the "public" endpoint
				.requestMatchers("/public/**").permitAll()
				// Allow access to the "/client-only" endpoint only with "SCOPE_client_read"
				.requestMatchers("/client-only").hasAuthority("SCOPE_client_read")
				// Other endpoints require authentication but no specific scope
				.anyRequest().authenticated()
				.and()
				.oauth2ResourceServer()
				.opaqueToken(); // Use opaque tokens
		return http.build();
	}

}