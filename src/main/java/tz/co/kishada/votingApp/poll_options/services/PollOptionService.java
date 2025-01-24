package tz.co.kishada.votingApp.poll_options.services;


import org.springframework.http.ResponseEntity;
import tz.co.kishada.votingApp.poll_options.dtos.PollOptionDto;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */


public interface PollOptionService {

   ResponseEntity<?> createPollOption(PollOptionDto pollOptionDto);

}
