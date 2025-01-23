package tz.co.kishada.votingApp.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggedInUserDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String username;
    private String name;
    private String mobileNumber;
    private String email;
    private UserType userType;

    private Set<String> permissions;
}
