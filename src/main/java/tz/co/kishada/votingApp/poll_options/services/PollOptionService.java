package tz.co.kishada.votingApp.poll_options.services;


import org.springframework.http.ResponseEntity;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.entities.PollOption;
import tz.co.kishada.votingApp.poll_options.dtos.PollOptionDto;

import java.util.List;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */


public interface PollOptionService {

   ResponseEntity<?> createPollOption(PollOptionDto pollOptionDto);

   ResponseEntity<?> getPollOptionByPoll(String pollUuid);

   List<PollOption> listPollOptionByPoll(Poll poll);

   ResponseEntity<?> deletePollOption(String pollOptionUid);
}
