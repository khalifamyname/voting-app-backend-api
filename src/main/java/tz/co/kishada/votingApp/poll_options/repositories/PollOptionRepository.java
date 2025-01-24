package tz.co.kishada.votingApp.poll_options.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.entities.PollOption;

import java.util.List;
import java.util.Optional;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/24/25
 */

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Long>, PagingAndSortingRepository<PollOption, Long> {
    Optional<PollOption> findFirstByUuid(String uuid);

    List<PollOption> findAllByPoll(Poll poll);
    List<PollOption> findAllByPoll_Id(Long pollId);
    List<PollOption> findAllByPoll_IdAndActiveTrueAndDeletedFalse(Long pollId);
    List<PollOption> findAllByPollAndActiveTrueAndDeletedFalse(Poll poll);
}
