package gram.killergram.domain.vote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "vote_user")
@Getter
@NoArgsConstructor
@Table(name = "vote_user_tbl")
public class VoteUser {

    @Id
    @Column(name = "vote_student",unique = true, nullable = false)
    private UUID voteStudent;

    @Column(name = "is_attend", nullable = false)
    private boolean isAttend;

    

    @Builder
    public VoteUser(boolean isAttend) {
        this.voteStudent = UUID.randomUUID();
        this.isAttend = isAttend;
    }
}
