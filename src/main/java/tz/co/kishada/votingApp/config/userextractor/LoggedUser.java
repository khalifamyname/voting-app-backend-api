package tz.co.kishada.votingApp.config.userextractor;

import org.springframework.stereotype.Component;


@Component
public interface LoggedUser {
    UserDetails getInfo();
}
