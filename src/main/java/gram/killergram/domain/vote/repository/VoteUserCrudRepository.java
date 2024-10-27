package gram.killergram.domain.vote.repository;

import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.vote.domain.VoteUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteUserCrudRepository extends CrudRepository<VoteUser, UUID> {
    public Optional<VoteUser> findByStudentId(Student studentId);
}
