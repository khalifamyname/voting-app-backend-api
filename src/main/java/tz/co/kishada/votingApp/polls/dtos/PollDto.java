package tz.co.kishada.votingApp.polls.dtos;


import lombok.Getter;
import lombok.Setter;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */

@Getter
@Setter
public class PollDto {
    private String question;
    private String uuid;
}
