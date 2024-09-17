package gram.killergram.domain.user.repository;

import gram.killergram.domain.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenJpaRepository extends CrudRepository<RefreshToken, String> {
}
