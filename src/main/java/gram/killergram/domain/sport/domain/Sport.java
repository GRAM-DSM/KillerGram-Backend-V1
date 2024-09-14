package gram.killergram.domain.sport.domain;

import gram.killergram.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Sport {

    @Id
    @Column(name = "sport_id",unique = true, nullable = false)
    private UUID sportId;

    @OneToOne(mappedBy = "sport", cascade = CascadeType.ALL)
    private Student manager_id;

    @Column(name = "sport_name", nullable = false, columnDefinition = "VACHAR(30)")
    private String sportName;

    @Column(name = "is_position", nullable = false)
    private boolean isPosition;

    @Column(name = "personnel", nullable = false)
    private Integer personnel;
}
