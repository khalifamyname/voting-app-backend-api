package tz.co.kishada.votingApp.votes.services;


import org.springframework.http.ResponseEntity;
import tz.co.kishada.votingApp.votes.dtos.VoteDto;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 2/1/25
 */


public interface VoteService {
    ResponseEntity<?> createVote(VoteDto voteDto);
    ResponseEntity<?> getPollVotes(String pollUuid);
}
