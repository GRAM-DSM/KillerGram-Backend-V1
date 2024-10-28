package gram.killergram.domain.vote.repository;

import gram.killergram.domain.vote.domain.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface VoteCrudRepository extends CrudRepository<Vote, UUID> {

    public List<Vote> findByVoteDate(LocalDate voteDate);

}
