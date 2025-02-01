package tz.co.kishada.votingApp.votes.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tz.co.kishada.votingApp.entities.Vote;

import java.util.Optional;

/**
 * @author Khalifa K. Twaha
 * @email khalifa.twaha@ega.go.tz
 * @since 2/1/25
 */


public interface VoteRepository extends JpaRepository<Vote, Long>, PagingAndSortingRepository<Vote, Long> {
    Optional<Vote> findFirstByUuid(String uuid);
}
