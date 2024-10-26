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

    @Column(name = "vote_position", nullable = true)
    private Integer votePosition;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "vote_id", nullable = false)
    private Vote vote;

    @Builder
    public VoteUser(boolean isAttend, Integer votePosition, Student student, Vote vote) {
        this.voteStudent = UUID.randomUUID();
        this.votePosition = votePosition;
        this.student = student;
        this.vote = vote;
        this.isAttend = isAttend;
    }
}
