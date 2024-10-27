package gram.killergram.domain.email.repository;

import gram.killergram.domain.email.domain.Email;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EmailCrudRepository extends CrudRepository<Email, String> {

    // To delete garbage authentication information
    @Modifying
    @Transactional
    void deleteByCertifiedTimeBefore(LocalDateTime currentTime);
}
