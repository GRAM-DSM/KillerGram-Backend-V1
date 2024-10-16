package gram.killergram.domain.sport.domain;

import gram.killergram.domain.sport.domain.type.SportName;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "sport")
@Getter
@NoArgsConstructor
@Table(name = "sport_tbl")
public class Sport {

    @Id
    @Column(name = "sport_id", unique = true, nullable = false)
    private UUID sportId;

    @Column(name = "manager_email")
    private String managerEmail;

    @Column(name = "sport_name", nullable = false, columnDefinition = "VARCHAR(30)")
    private String sportName;

    @Column(name = "is_position", nullable = false)
    private boolean isPosition;

    @Column(name = "personnel", nullable = false)
    private Integer personnel;

    @Builder
    public Sport(String managerEmail, String sportName, boolean isPosition, Integer personnel) {
        this.managerEmail = managerEmail;
        this.sportId = UUID.randomUUID();
        this.sportName = sportName;
        this.isPosition = isPosition;
        this.personnel = personnel;
    }

    public SportName getSportName() {
        return SportName.valueOf(sportName);
    }
}

