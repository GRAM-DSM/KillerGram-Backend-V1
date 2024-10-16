package gram.killergram.domain.sport.repository;

import gram.killergram.domain.sport.domain.Sport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SportCrudRepository extends CrudRepository<Sport, UUID> {

}
