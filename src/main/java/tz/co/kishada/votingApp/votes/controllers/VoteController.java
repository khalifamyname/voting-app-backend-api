package tz.co.kishada.votingApp.votes.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tz.co.kishada.votingApp.poll_options.dtos.PollOptionDto;
import tz.co.kishada.votingApp.votes.dtos.VoteDto;
import tz.co.kishada.votingApp.votes.services.VoteService;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 2/1/25
 */

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVote(@RequestBody VoteDto voteDto){
        return voteService.createVote(voteDto);
    }

}
