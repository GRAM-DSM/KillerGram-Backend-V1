package gram.killergram.domain.email.repository;

import gram.killergram.domain.email.domain.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCrudRepository extends CrudRepository<Email, String> {
}
