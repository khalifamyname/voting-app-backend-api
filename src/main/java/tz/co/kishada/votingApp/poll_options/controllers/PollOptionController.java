package tz.co.kishada.votingApp.poll_options.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tz.co.kishada.votingApp.poll_options.dtos.PollOptionDto;
import tz.co.kishada.votingApp.poll_options.services.PollOptionService;
import tz.co.kishada.votingApp.polls.dtos.PollDto;
import tz.co.kishada.votingApp.polls.services.PollService;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */

@RestController
@RequestMapping("/api/v1/poll-options")
@RequiredArgsConstructor
public class PollOptionController {

    private final PollOptionService pollOptionService;

    //   @PreAuthorize("hasRole('ROLE_CREATE_POLL_OPTION')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPollOption(@RequestBody PollOptionDto pollOptionDto){
        return pollOptionService.createPollOption(pollOptionDto);
    }
}
