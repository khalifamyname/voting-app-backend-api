package tz.co.kishada.votingApp.polls.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import tz.co.kishada.votingApp.config.userextractor.LoggedUser;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.enums.PollStatus;
import tz.co.kishada.votingApp.polls.dtos.PollDto;
import tz.co.kishada.votingApp.polls.repositories.PollRepository;
import tz.co.kishada.votingApp.response.KishadaListResponseWrapper;
import tz.co.kishada.votingApp.response.KishadaResponseWrapper;
import tz.co.kishada.votingApp.utils.GlobalMethod;
import tz.co.kishada.votingApp.utils.KishadaHelper;
import tz.co.kishada.votingApp.utils.KishadaResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private static final Logger logger = LoggerFactory.getLogger(PollServiceImpl.class);

    public final GlobalMethod globalMethod;
    public final PollRepository pollRepository;
    private final LoggedUser loggedUser;

    @Override
    public ResponseEntity<?> createPoll(PollDto pollDto) {
        logger.info("[Poll]: Saving Poll at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaResponseWrapper<Poll> responseWrapper = new KishadaResponseWrapper<>();
        Integer statusCode = KishadaResponseCode.SUCCESS;
        String description = "Successfully";

        try {

            if (loggedUser.getInfo() == null) {
                return globalMethod.response(KishadaResponseCode.INVALID_REQUEST, "User detail not found!",   responseWrapper);
            }

            KishadaHelper.print(pollDto);

            Poll poll = new Poll();
            if (pollDto.getUuid() == null && pollDto.getQuestion() == null) {
                return globalMethod.response(KishadaResponseCode.DATA_MISSED_IN_PAYLOAD, "Please fill all required fields!",   responseWrapper);
            }

            if (pollDto.getUuid() != null) {
                if (optionalPoll(pollDto.getUuid()).isPresent()) {
                    poll = optionalPoll(pollDto.getUuid()).get();
                    if (poll.getStatus().equals(PollStatus.SUBMITTED)){
                        return globalMethod.response(KishadaResponseCode.RESTRICTED_ACCESS, "You cannot edit this poll!", responseWrapper);
                    }
                }
                else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Selected poll couldn't be found!", responseWrapper);
            }

            if (pollDto.getQuestion() != null)
                poll.setQuestion(pollDto.getQuestion());

            poll.setStatus(PollStatus.DRAFT);
            poll.setCreatedBy(loggedUser.getInfo().getId());
            Poll savedPoll = pollRepository.save(poll);

            responseWrapper.setItem(savedPoll);
            return globalMethod.response(statusCode, description, responseWrapper);

        }   catch (Exception e) {
            logger.error("Exception occurred during savePoll");
            e.printStackTrace();
            statusCode = KishadaResponseCode.FAILURE;
            description = "Failed";
            return  globalMethod.response(statusCode, description,responseWrapper);
        }
    }

    @Override
    public ResponseEntity<?> getPollList(Integer pageNo, Integer pageSize, String[]sort) {
        logger.info("[Poll]: Get Poll at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaListResponseWrapper responseWrapper = new KishadaListResponseWrapper();
        Integer statusCode = KishadaResponseCode.SUCCESS;
        String description = "Successfully";
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(globalMethod.sortByParameter(sort)));
        try {
            Page<Poll> polls = pollRepository.findByDeletedFalseAndActiveTrue(pageable);
            if (polls.isEmpty()){
                statusCode = KishadaResponseCode.NO_RECORD_FOUND;
                description = "Poll details not found!";
                return globalMethod.response(statusCode, description, responseWrapper);
            }
            responseWrapper.setResponse(polls);
            return  globalMethod.response(statusCode, description, responseWrapper);

        }   catch (Exception e){
            logger.error("Error in listing poll details: {}{}", e);
            statusCode = KishadaResponseCode.FAILURE;
            description = "Failed!";
            return globalMethod.response(statusCode, description, responseWrapper);

        }
    }

    @Override
    public ResponseEntity<?> findPoll(String pollUid) {
        logger.info("[Poll]: Find Poll at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaResponseWrapper<Poll> responseWrapper = new KishadaResponseWrapper<>();
        try {
            if (optionalPoll(pollUid).isPresent()) {
                responseWrapper.setItem(optionalPoll(pollUid).get());
                return globalMethod.response(KishadaResponseCode.SUCCESS, "Completed successfully!", responseWrapper);
            }
            else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Poll couldn't be found!", responseWrapper);
        } catch (Exception e) {
            logger.error("Exception occurred on find Poll");
            e.printStackTrace();
            return  globalMethod.response(KishadaResponseCode.FAILURE, "Sorry execution failed!", responseWrapper);
        }
    }

    @Override
    public ResponseEntity<?> submitPoll(String pollUid) {
        logger.info("[Poll]: Submitting Poll at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaResponseWrapper<Poll> responseWrapper = new KishadaResponseWrapper<>();
        try {
            if (optionalPoll(pollUid).isPresent()) {
                Poll poll = optionalPoll(pollUid).get();
                if (poll.getOptions().isEmpty()){
                    return globalMethod.response(KishadaResponseCode.INVALID_REQUEST, "Poll has no options, add options first!", responseWrapper);
                }
                long countSubmittedPoll = pollRepository.countByPollNumberIsNotNull();
                long newCount = countSubmittedPoll + 1;
                String formattedCount = String.format("%02d", newCount);
                String pollNumber = "P/"+LocalDateTime.now().getYear()+"/"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM")).toUpperCase()+ "/" + formattedCount;
                poll.setStatus(PollStatus.SUBMITTED);
                poll.setPollNumber(pollNumber);
                responseWrapper.setItem(pollRepository.save(poll));
                return globalMethod.response(KishadaResponseCode.SUCCESS, "Completed successfully!", responseWrapper);
            }
            else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Poll couldn't be found!", responseWrapper);
        } catch (Exception e) {
            logger.error("Exception occurred on poll submit");
            e.printStackTrace();
            return  globalMethod.response(KishadaResponseCode.FAILURE, "Sorry execution failed!", responseWrapper);
        }
    }

    @Override
    public Optional<Poll> optionalPoll(String uuid) {
        return pollRepository.findFirstByUuid(uuid);
    }

}
