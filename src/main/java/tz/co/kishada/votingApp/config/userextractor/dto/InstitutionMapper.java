package tz.co.kishada.votingApp.config.userextractor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class InstitutionMapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String phone;
    private UUID uuid;
}
