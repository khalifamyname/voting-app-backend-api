package tz.co.kishada.votingApp.config.userextractor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {
    private Long id;
    private String email;
    private String username;
    private String name;
    private String mobileNumber;
    private boolean enabled;

    private String userType;
    private List<String> rolesList;

    private List<String> permissions;

    private boolean accountNonExpired;

    private boolean credentialsNonExpired;

    private boolean accountNonLocked;
    private UUID uuid;

}
