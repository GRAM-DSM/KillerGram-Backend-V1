package gram.killergram.domain.vote.domain;

import gram.killergram.domain.user.domain.Student;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    @Builder
    public VoteUser(boolean isAttend) {
        this.voteStudent = UUID.randomUUID();
        this.isAttend = isAttend;
    }
}
