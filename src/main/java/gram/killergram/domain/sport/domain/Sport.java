package gram.killergram.domain.sport.domain;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.user.domain.Student;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "sport")
@Getter
@NoArgsConstructor
@Table(name = "sport_tbl")
public class Sport {

    @Id
    @Column(name = "sport_id", unique = true, nullable = false)
    private UUID sportId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private Student managerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport_name", nullable = false, columnDefinition = "VARCHAR(30)")
    private SportName sportName;

    @Column(name = "is_position", nullable = false)
    private boolean isPosition;

    @Column(name = "personnel", nullable = false)
    private Integer personnel;

    @Builder
    public Sport(Student managerId, SportName sportName, boolean isPosition, Integer personnel) {
        this.managerId = managerId;
        this.sportId = UUID.randomUUID();
        this.sportName = sportName;
        this.isPosition = isPosition;
        this.personnel = personnel;
    }
}

