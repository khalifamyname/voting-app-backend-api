package tz.co.kishada.votingApp.poll_options.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tz.co.kishada.votingApp.config.userextractor.LoggedUser;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.entities.PollOption;
import tz.co.kishada.votingApp.enums.PollStatus;
import tz.co.kishada.votingApp.poll_options.dtos.PollOptionDto;
import tz.co.kishada.votingApp.poll_options.repositories.PollOptionRepository;
import tz.co.kishada.votingApp.polls.repositories.PollRepository;
import tz.co.kishada.votingApp.response.KishadaResponseWrapper;
import tz.co.kishada.votingApp.utils.GlobalMethod;
import tz.co.kishada.votingApp.utils.KishadaHelper;
import tz.co.kishada.votingApp.utils.KishadaResponseCode;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */

@Service
@RequiredArgsConstructor
public class PollOptionServiceImpl implements PollOptionService {

    private static final Logger logger = LoggerFactory.getLogger(PollOptionServiceImpl.class);

    public final PollOptionRepository pollOptionRepository;
    public final PollRepository pollRepository;
    public final GlobalMethod globalMethod;
    private final LoggedUser loggedUser;

    @Override
    public ResponseEntity<?> createPollOption(PollOptionDto pollOptionDto) {
        logger.info("[Poll]: Saving Poll Option at {} by {}", LocalDateTime.now(), loggedUser.getInfo().getEmail());
        KishadaResponseWrapper<PollOption> responseWrapper = new KishadaResponseWrapper<>();
        Integer statusCode = KishadaResponseCode.SUCCESS;
        String description = "Successfully";

        try {

            if (loggedUser.getInfo() == null) {
                return globalMethod.response(KishadaResponseCode.INVALID_REQUEST, "User detail not found!",   responseWrapper);
            }

            KishadaHelper.print(pollOptionDto);

            PollOption pollOption = new PollOption();
            if (pollOptionDto.getUuid() == null && pollOptionDto.getTitle() == null) {
                return globalMethod.response(KishadaResponseCode.DATA_MISSED_IN_PAYLOAD, "Please fill all required fields!",   responseWrapper);
            }

            if (pollOptionDto.getUuid() != null) {
                Optional<PollOption> optionalPollOption = pollOptionRepository.findFirstByUuid(pollOptionDto.getUuid());
                if (optionalPollOption.isPresent()) {
                    pollOption = optionalPollOption.get();
                }
                else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Selected poll option couldn't be found!", responseWrapper);
            }

            if (pollOptionDto.getPollUuid() != null) {
                Optional<Poll> optionalPoll = pollRepository.findFirstByUuid(pollOptionDto.getPollUuid());
                if (optionalPoll.isPresent()) {
                    pollOption.setPoll(optionalPoll.get());
                }
                else return globalMethod.response(KishadaResponseCode.NO_RECORD_FOUND, "Selected poll couldn't be found!", responseWrapper);
            }

            if (pollOptionDto.getTitle() != null)
                pollOption.setTitle(pollOptionDto.getTitle());
            pollOption.setCreatedBy(loggedUser.getInfo().getId());
            PollOption savedPollOption = pollOptionRepository.save(pollOption);

            responseWrapper.setItem(savedPollOption);
            return globalMethod.response(statusCode, description, responseWrapper);

        }   catch (Exception e) {
            logger.error("Exception occurred creating poll option");
            e.printStackTrace();
            statusCode = KishadaResponseCode.FAILURE;
            description = "Sorry, execution failed!";
            return  globalMethod.response(statusCode, description,responseWrapper);
        }
    }
}
