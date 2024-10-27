package gram.killergram.domain.vote.repository;

import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.vote.domain.VoteUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteUserCrudRepository extends CrudRepository<VoteUser, UUID> {
    public Optional<VoteUser> findByStudentId(Student studentId);
}
