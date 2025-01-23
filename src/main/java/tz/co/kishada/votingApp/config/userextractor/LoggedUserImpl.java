package tz.co.kishada.votingApp.config.userextractor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tz.co.kishada.votingApp.config.userextractor.dto.CouncilMapper;
import tz.co.kishada.votingApp.config.userextractor.dto.InstitutionMapper;
import tz.co.kishada.votingApp.config.userextractor.dto.WardMapper;
import tz.co.kishada.votingApp.utils.CustomGeneratedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class LoggedUserImpl implements LoggedUser {

    private static final Logger logger = LoggerFactory.getLogger(LoggedUserImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails getInfo() {

        final String principalConstant = "principal";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (null == auth) {
            System.out.println("auth is null");
            return null;
        } else {
            UserDetails user = new UserDetails();

            try {
                ObjectMapper mapper = new ObjectMapper();
                String jsonAuth = mapper.writeValueAsString(auth);

                HashMap<String, Object> result = new ObjectMapper().readValue(jsonAuth, HashMap.class);
                if (result.get(principalConstant) != null) {

            HashMap<String, Object> userAuthentication = (HashMap<String, Object>) result.get("principal");

            HashMap<String, Object> details = (HashMap<String, Object>) userAuthentication.get("attributes");

            if (details != null && CustomGeneratedData.isObjectHashMap(details)) {
                System.out.println("principal is not null");
                HashMap<String, Object> userDetails = details;
                Object id = userDetails.get("id");
                Object email = userDetails.get("email");
                Object enabled = userDetails.get("enabled");

                Object firstName = userDetails.get("firstName");
                Object middleName = userDetails.get("middleName");
                Object lastName = userDetails.get("lastName");
                Object userType = userDetails.get("userType");
                Object uuid = userDetails.get("uuid");

                user.setName(firstName + " " + middleName + " " + lastName);
                user.setId(Long.parseLong(id.toString()));

                user.setName(firstName + " " + middleName + " " + lastName);
                user.setId(Long.parseLong(id.toString()));

                user.setEmail(email.toString());
                user.setUsername(email.toString());
                user.setEnabled(Boolean.valueOf(enabled.toString()));
                user.setUserType(userType.toString());
                user.setUuid(UUID.fromString(uuid.toString()));
                System.out.println("=====USER DATAZ=====");
                //CropHelper.print(user);
                System.out.println("=====USER DATAZ=====");
                return user;
            } else{
                return null;
            }
        }
    } catch( Exception e){
        logger.error("----- Error has occured on authentication facade {} -------", e.getMessage());
        e.printStackTrace();
    }
}
        return null;
                }


                }

