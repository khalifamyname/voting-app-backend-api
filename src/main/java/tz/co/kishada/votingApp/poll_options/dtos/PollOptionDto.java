package tz.co.kishada.votingApp.poll_options.dtos;


import lombok.Data;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */

@Data
public class PollOptionDto {
    private String title;
    private String pollUuid;
    private String uuid;
}
