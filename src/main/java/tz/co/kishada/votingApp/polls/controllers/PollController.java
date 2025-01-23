package tz.co.kishada.votingApp.polls.controllers;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tz.co.kishada.votingApp.polls.dtos.PollDto;
import tz.co.kishada.votingApp.polls.services.PollService;
import lombok.RequiredArgsConstructor;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */


@RequestMapping("/api/v1/polls")
@RestController
@RequiredArgsConstructor
public class PollController {

   private final PollService pollService;

//   @PreAuthorize("hasRole('ROLE_CREATE_POLL')")
   @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> createPoll(@RequestBody PollDto pollDto){
      return pollService.createPoll(pollDto);
   }

   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> getPollList(
           @RequestParam(defaultValue = "0") Integer pageNo,
           @RequestParam(defaultValue = "10") Integer pageSize,
           @RequestParam(defaultValue = "createdAt, desc") String[]sortBy
   ){
      return pollService.getPollList(pageNo, pageSize, sortBy);
   }

   @GetMapping(value = "/uuid/{pollUid}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> findPoll(@PathVariable String pollUid){
      return pollService.findPoll(pollUid);
   }

   @PutMapping(value = "/submit/{pollUid}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> submitPoll(@PathVariable String pollUid){
      return pollService.submitPoll(pollUid);
   }

   @DeleteMapping(value = "/uuid/{pollUid}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<?> deletePoll(@PathVariable String pollUid){
      return pollService.deletePoll(pollUid);
   }

   @GetMapping(value = "/test-poll", produces = MediaType.APPLICATION_JSON_VALUE)
   public String testPoll() {
      return "pollService.testPoll()";
   }

}
