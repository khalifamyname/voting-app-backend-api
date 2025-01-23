package tz.co.kishada.votingApp.polls.services;


import org.springframework.http.ResponseEntity;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.polls.dtos.PollDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */


public interface PollService {
    ResponseEntity<?> createPoll(PollDto pollDto);

    ResponseEntity<?> getPollList(Integer pageNo, Integer pageSize, String[]sort);

    ResponseEntity<?> findPoll(String pollUid);

    ResponseEntity<?> submitPoll(String pollUid);

    Optional<Poll> optionalPoll(String uuid);

    ResponseEntity<?> deletePoll(String pollUid);
}
