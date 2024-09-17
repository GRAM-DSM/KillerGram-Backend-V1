package gram.killergram.domain.user.repository;

import gram.killergram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByAccountId(String accountId);
}
