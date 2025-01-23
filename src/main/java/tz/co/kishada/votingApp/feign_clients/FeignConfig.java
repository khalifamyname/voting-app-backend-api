package tz.co.kishada.votingApp.feign_clients;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 12/6/24
 *
 */


@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = getOpaqueTokenFromSecurityContext();
            if (token != null) {
                System.out.println(":::: TOKEN: " + token);
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

    private String getOpaqueTokenFromSecurityContext() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof BearerTokenAuthentication) {
            return ((BearerTokenAuthentication) authentication).getToken().getTokenValue();
        }
        return null;
    }
}
