package tz.co.kishada.votingApp.feign_clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tz.co.kishada.votingApp.config.userextractor.UserDetails;

import java.util.UUID;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 12/6/24
 *
 */


@FeignClient(value = "user-service", url = "${user.service.url}", configuration = FeignConfig.class)
public interface UserServiceFeignClient {

    /*@GetMapping("/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);*/

    @GetMapping("/api/v1/user/{uuid}")
    UserDetails getUserByUuid(@PathVariable("uuid") UUID uuid);

}
