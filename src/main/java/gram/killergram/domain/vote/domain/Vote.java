package gram.killergram.domain.vote.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "vote")
@Getter
@NoArgsConstructor
@Table(name = "vote_tbl")
public class Vote {

    @Id
    @Column(name = "vote_id",unique = true, nullable = false)
    private UUID voteId;

    @Column(name = "vote_date", nullable = false)
    private String voteDate;

    @Column(name = "vote_position", nullable = true)
    private Integer votePosition;

    @Column(name = "participate", nullable = false)
    private Integer participate;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @Builder
    public Vote(String voteDate, Integer votePosition, Integer participate, boolean isEnd) {
        this.voteId = UUID.randomUUID();
        this.voteDate = voteDate;
        this.votePosition = votePosition;
        this.participate = participate;
        this.isEnd = isEnd;
    }
}
