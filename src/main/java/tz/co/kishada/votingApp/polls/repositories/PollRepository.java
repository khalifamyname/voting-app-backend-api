package tz.co.kishada.votingApp.polls.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tz.co.kishada.votingApp.entities.Poll;
import tz.co.kishada.votingApp.enums.PollStatus;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 1/20/25
 */

@Repository
public interface PollRepository extends JpaRepository<Poll, Long>, PagingAndSortingRepository<Poll, Long> {

    Optional<Poll> findFirstByUuid(String uuid);

    Page<Poll> findAll(Pageable pageable);

    Page<Poll> findByStatusAndDeletedFalseAndActiveTrue(PollStatus status, Pageable pageable);

    Page<Poll> findByDeletedFalseAndActiveTrue(Pageable pageable);

    boolean existsByPollNumber(String idNumber);

    long countByPollNumberIsNotNull();
}