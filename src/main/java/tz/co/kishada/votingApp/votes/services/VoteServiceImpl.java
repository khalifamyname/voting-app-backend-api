package tz.co.kishada.votingApp.votes.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tz.co.kishada.votingApp.config.userextractor.LoggedUser;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.entities.PollOption;
import tz.co.kishada.votingApp.entities.Vote;
import tz.co.kishada.votingApp.poll_options.repositories.PollOptionRepository;
import tz.co.kishada.votingApp.polls.repositories.PollRepository;
import tz.co.kishada.votingApp.response.KishadaResponseWrapper;
import tz.co.kishada.votingApp.utils.GlobalMethod;
import tz.co.kishada.votingApp.utils.KishadaHelper;
import tz.co.kishada.votingApp.utils.KishadaResponseCode;
import tz.co.kishada.votingApp.votes.dtos.VoteDto;
import tz.co.kishada.votingApp.votes.repositories.VoteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 2/1/25
 */

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private static final Logger logger = LoggerFactory.getLogger(VoteServiceImpl.class);

    private final VoteRepository voteRepository;
    public final PollOptionRepository pollOptionRepository;
    public final PollRepository pollRepository;
    public final GlobalMethod globalMethod;
    private final LoggedUser loggedUser;


    @Override
    public ResponseEntity<?> createVote(VoteDto voteDto) {
        logger.info("[Vote]: Saving Vote at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaResponseWrapper<Vote> responseWrapper = new KishadaResponseWrapper<>();
        Integer statusCode = KishadaResponseCode.SUCCESS;
        String description = "Completed successfully";

        try {

            if (loggedUser.getInfo() == null) {
                return globalMethod.response(KishadaResponseCode.INVALID_REQUEST, "User detail not found!",   responseWrapper);
            }

            KishadaHelper.print(voteDto);

            Vote vote = new Vote();
            if (voteDto.getOptionUuid() == null) {
                return globalMethod.response(KishadaResponseCode.DATA_MISSED_IN_PAYLOAD, "Please select option to cast your vote!",   responseWrapper);
            }

            PollOption pollOption;
            Optional<PollOption> optionalPollOption = pollOptionRepository.findFirstByUuid(voteDto.getOptionUuid());
            if (optionalPollOption.isPresent()) {
                pollOption = optionalPollOption.get();
            }
            else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Selected poll option does not exist!", responseWrapper);

            Poll poll = pollOption.getPoll();
            vote.setPollOption(pollOption);
            vote.setPoll(poll);
            vote.setVoterId(loggedUser.getInfo().getId());
            vote.setVoterName(loggedUser.getInfo().getName());
            vote.setSelectedOpinion(pollOption.getTitle());
            vote.setCreatedBy(loggedUser.getInfo().getId());
            Vote newVote = voteRepository.save(vote);

            poll.getVotes().add(newVote);
            pollOption.getVotes().add(newVote);
            pollOptionRepository.save(pollOption);
            pollRepository.save(poll);

            responseWrapper.setItem(newVote);
            return globalMethod.response(statusCode, description, responseWrapper);

        }   catch (Exception e) {
            logger.error("[Vote]: Error casting vote: {}", e.getMessage());
            e.printStackTrace();
            statusCode = KishadaResponseCode.FAILURE;
            description = "Sorry, execution failed!";
            return  globalMethod.response(statusCode, description,responseWrapper);
        }
    }

    @Override
    public ResponseEntity<?> getPollVotes(String pollUuid) {
        return null;
    }
}
