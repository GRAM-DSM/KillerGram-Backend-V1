package gram.killergram.domain.vote.repository;

import gram.killergram.domain.vote.domain.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteCrudRepository extends CrudRepository<Vote, UUID> {

}
