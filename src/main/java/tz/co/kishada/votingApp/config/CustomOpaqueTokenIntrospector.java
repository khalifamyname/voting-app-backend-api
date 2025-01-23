package tz.co.kishada.votingApp.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    @Value("${introspector.uri}")
    private String introspectUri;

    @Value("${user-info-uri}")
    private String userInfoURI;
    @Value("${uaa.client-id}")
    private String introspectClientId;
    @Value("${uaa.client-secret}")
    private String introspectClientSecret;

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        log.info("settings introspecting token at {} with introspector uri: {}", LocalDateTime.now(), introspectUri);

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", "Bearer " + token);
            headers.add("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("token", token);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
           log.info("urls is : user info {} and introspection {} ",userInfoURI,introspectUri);
            ResponseEntity<?> result = restTemplate.exchange(userInfoURI, HttpMethod.GET, entity, String.class);
            ObjectMapper objMap = new ObjectMapper();

            JsonNode resultNode = objMap.readTree(result.getBody().toString());
//            CropHelper.print(resultNode);

//            UserDetails attributes = objMap.readValue(resultNode.get("data").toString(), UserDetails.class);
            Map<String, Object> attributes = objMap.readValue(resultNode.get("data").toString(), Map.class);
            System.out.println("==============USER DATA===============");
            //CropHelper.print(attributes);
            System.out.println("==============USER DATA===============");
            List<AuthorityDto> mappedAuth = objMap.convertValue(resultNode.get("authorities"), new TypeReference<List<AuthorityDto>>() {
                    });
            System.out.println("========TESTS=================");
            OpaqueTokenIntrospector delegate = new NimbusOpaqueTokenIntrospector(introspectUri, introspectClientId,
                    introspectClientSecret);
            OAuth2AuthenticatedPrincipal principal = delegate.introspect(token);
//            log.info("principal: {}", principal.getName());
            return new OAuth2IntrospectionAuthenticatedPrincipal(principal.getName(), attributes, mappedAuth
                    .stream().map(AuthorityDto::getAuthority).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
        } catch (Exception e) {
            log.error("Error occurred in introspection: ", e);
        }
        return null;
    }

}

