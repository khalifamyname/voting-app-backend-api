package tz.co.kishada.votingApp.config.userextractor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class WardMapper implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private UUID uuid;
    private String name;
}
