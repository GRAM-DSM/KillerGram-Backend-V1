package gram.killergram.domain.user.repository;

import gram.killergram.domain.user.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentJpaRepository extends JpaRepository<Student, UUID> {
}
